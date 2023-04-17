package com.fanpower.lib.ui.fragment


import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fanpower.lib.R
import com.fanpower.lib.adapter.AnswerListAdapter
import com.fanpower.lib.api.ApiFactory
import com.fanpower.lib.api.ApiManager
import com.fanpower.lib.api.model.*
import com.fanpower.lib.databinding.FragmentQuestionsBinding
import com.fanpower.lib.interfaces.*
import com.fanpower.lib.utils.Constants.Extra.PropExtra
import com.fanpower.lib.utils.Constants.Generic.PostPickAdId
import com.fanpower.lib.utils.Constants.Generic.PrePickAdId
import com.fanpower.lib.utils.Constants.Generic.VerifyAdId
import com.fanpower.lib.utils.SharedPrefs
import com.fanpower.lib.utils.Utilities
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuestionsFragment(onsucessCallback : VerificationPopUpShownCallback,webView: WebView) : Fragment() {

    private var emailForVerification = ""
    private var phoneNumberForVerification = ""
    private val TAG = "QuestionsFragment"
    private var onsucessCallback : VerificationPopUpShownCallback

    private lateinit var binding: FragmentQuestionsBinding

    private lateinit var webView: WebView

    var view_: View? = null

    lateinit var timer: CountDownTimer

    lateinit var prop: Prop

     var isEditing = false


    var enterCodeMode = false
    var isEmailMode = false
    var selectedPickId = ""
    var selectedPickTitle = ""

//    var scrollX =0
//    var scrollY = 0;

    lateinit var adapter : AnswerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    init {
        this.onsucessCallback = onsucessCallback
        this.webView = webView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        view_ = binding.root

//        requireActivity().getWindow()
//            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        val bundle = arguments
        if (bundle != null) {
            prop = bundle.getSerializable(PropExtra) as Prop
            Log.i(TAG, "onCreateView: prop id " + prop.id)
            setUpView()
        }

        return view_
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpView() {
        getProps()

        if (!SharedPrefs.Utils.getFanId(requireActivity()).isNullOrEmpty()) {
            getFanPicks()
        }

        binding.resendCodeText.setTextColor(SharedPrefs.Utils.getSecondaryColor(requireActivity()))
        binding.verifyTitle.setTextColor(SharedPrefs.Utils.getTextLinkColor(requireActivity()))
        Utilities.getVerifyTitleBackground(binding.verifyTitle,SharedPrefs.Utils.getSecondaryColor(requireActivity()),0)

        binding.title.setText(prop.proposition)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        binding.countryCodePicker.registerCarrierNumberEditText(binding.editTextCarrierNumber)

        binding.countryCodePicker.setDefaultCountryUsingNameCode("US")

        binding.editTextCarrierNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isEditing = true
            }

            override fun afterTextChanged(editable: Editable?) {
                Log.i(TAG, "afterTextChanged: editable " + editable)
                if (editable.isNullOrEmpty()) {
                    binding.sendBtn.setBackgroundResource(R.drawable.send_disabled)
                } else {
                    binding.sendBtn.setBackgroundResource(R.drawable.send_selected)
                }

                if(webView != null)
                     webView.clearFocus()
            }
        })

//
//        webView.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//        }

//        webView.setOnTouchListener(View.OnTouchListener { view, motionEvent -> // your code here....
//            Log.i(TAG, "setUpView: on touch listener webview")
//            if(isEditing){
//                Log.i(TAG, "setUpView: is editext visible")
//                webView.clearFocus()
//                isEditing = false
//                true
//            }else {
//                false
//            }
//        })

//        binding.editTextCarrierNumber.setOnClickListener {
//
//        }

//        binding.editTextCarrierNumber.setOnTouchListener(View.OnTouchListener { view, motionEvent -> // your code here....
//            Log.i(TAG, "setUpView: on touch listener edittext")
//
//            false
//        })

        binding.editTextCarrierNumber.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Log.d(TAG, "edittext : Focused Now!")

                Utilities.showKeyboard(requireActivity())
                webView.clearFocus()


            }else{
                Log.i(TAG, "edittext :  setUpView: not focused")
            }
        })

     //   Log.i(TAG, "setUpView: answers " + prop.picks.size)

        if(prop.picks != null) {
            var  picksForAdapter : ArrayList<PickForAdapter> = ArrayList<PickForAdapter>()
            for(x in prop.picks){
                var pick = PickForAdapter(x,false)
                picksForAdapter.add(pick)
            }

             adapter = AnswerListAdapter(picksForAdapter, object : OnClickCallBack {
                override fun onClick(pick: Pick) {

                    if (SharedPrefs.Utils.getFanId(requireActivity()).isNullOrEmpty()) {
                        userChooseAPick(pick.id, VerifyAdId,pick.title)
                        onsucessCallback.disableScroll()
                        binding.verifyIdentityLayout.visibility = View.VISIBLE

                    }else{
                        onsucessCallback.userPicked()
                        userChooseAPick(pick.id, PostPickAdId,pick.title)
                        adapter.setAnswerModeView(true)
                    }
                    Log.i(TAG, "onClick: ")
                  //  adapter.notifyDataSetChanged()
                }
             },requireActivity())
            // Setting the Adapter with the recyclerview
            binding.recyclerView.adapter = adapter
        }

        binding.sendBtn.setOnClickListener {
            if (binding.editTextCarrierNumber.text.toString().isNotEmpty()) {
                if (enterCodeMode) {
                    binding.progressBar.visibility = View.VISIBLE
                    var code =  binding.editTextCarrierNumber.text.toString().trim()
                    sendVerifyFanApi(code)
                } else {
                    Log.i(TAG, "setUpView: is not empty")
                    if(isEmailMode) {
                        sendEmailValidation()
                    }else{
                        sendAuthenticateFanApi()
                    }
                }
            } else {
                Log.i(TAG, "setUpView: is empty")
            }
        }

        binding.resendCodeText.setOnClickListener {
            if (enterCodeMode) {
                resetTimer()
                sendAuthenticateFanApi()
            } else {
                switchToOtherModeToVerify()
            }
        }


//        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                val position = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
//                Log.i(TAG, "onScrolled: position " + position)
//                if (position + 1 == prop.picks.size) {
//                    // END OF RECYCLERVIEW IS REACHED
//                    Log.i(TAG, "onScrolled: end of recyler view")
//                } else if(position == 0){
//                    Log.i(TAG, "onScrolled: start of position")
//                }else {
//                    Log.i(TAG, "onScrolled: not end or start")
//                    // END OF RECYCLERVIEW IS NOT REACHED
//                }
//            }
//        })

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val lastItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val firstItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                Log.i(TAG, "onScrolled: prop id " + prop.id + " first item position " + firstItemPosition)
                Log.i(TAG, "onScrolled:prop id  " + prop.id +  " last item position " + lastItemPosition)
                Log.i(TAG, "onScrolled: prps picks size " + prop.picks.size)
                if(firstItemPosition == 0 && lastItemPosition + 1 == prop.picks.size){
                    Log.i(TAG, "onScrolled: no need to show indicators")
                    // no need to show any indicator
                    binding.topScrollIndicator.visibility = View.GONE
                    binding.bottomScrollIndicator.visibility = View.GONE

                }else{
                    Log.i(TAG, "onScrolled: in else part")
                    if (!recyclerView.canScrollVertically(1) && dy > 0) {
                        //scrolled to BOTTOM
                        Log.i(TAG, "onScrolled: reached bottom")
                        binding.topScrollIndicator.visibility = View.VISIBLE
                        binding.bottomScrollIndicator.visibility = View.GONE

                    } else if (!recyclerView.canScrollVertically(-1) && dy < 0) {
                        //scrolled to TOP
                        Log.i(TAG, "onScrolled: reached top")
                        binding.topScrollIndicator.visibility = View.GONE
                        binding.bottomScrollIndicator.visibility = View.VISIBLE
                    }
                    if (firstItemPosition != 0){
                        Log.i(TAG, "onScrolled: showing first indicator")
                        binding.topScrollIndicator.visibility = View.VISIBLE
                    }
                    if(lastItemPosition + 1 != prop.picks.size){
                        binding.bottomScrollIndicator.visibility = View.VISIBLE
                    }
                }
            }
        })

        binding.recyclerView.smoothScrollToPosition(0);
    }

    private fun sendEmailValidation() {
        binding.progressBar.visibility = View.VISIBLE

        var email = binding.editTextCarrierNumber.text.toString().trim()
        var emailValidateBody = EmailValidateBody()
        emailValidateBody.email = email

        val responseEmailValidateBody: Call<String> =
            ApiFactory.getInstance()!!.validateEmail(SharedPrefs.Utils.getPublisherToken(activity),emailValidateBody)
        responseEmailValidateBody.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                binding.progressBar.visibility = View.GONE
                var validatedStr : String? = response.body()
                var errorStr : ResponseBody? = response.errorBody()
                Log.i(TAG, "onResponse: validated string " + validatedStr)
                if(validatedStr != null && (validatedStr.toLowerCase().equals("valid") || validatedStr.toLowerCase().equals("risky"))){
                    Log.i(TAG, "onResponse: email is valid ")
                    emailForVerification = email
                    sendVerifyFanApi("validated")
                }else{
                    Toast.makeText(activity,"Invalid Email",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Log.i(TAG, "onFailure: email " + t.message.toString())
                Toast.makeText(activity,t.message.toString(),Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun getFanPicks(){
        ApiManager.getFanPicks(requireActivity(),prop.id, object : FanPickCallback{
            override fun onSuccess(list: List<FanPickItem>?) {
                if (list != null) {
                    Log.i(TAG, "onSuccess: list size " + list.size)
                    for (x in list) {
                        for ((index, value) in prop.picks.withIndex()) {
                       //     Log.i(TAG, "onSuccess: prop id to compare "+ value.id)
                            if(x.pick_id.toString().equals(value.id)){
                                Log.i(TAG, "onSuccess: found at index " + index)
                                adapter.selectedIndex = index
                                adapter.setAnswerModeView(true)
                                adapter.notifyDataSetChanged()
                                break
                            }
                        }
                    }
                }
            }

            override fun onFailure(messageResponse: MessageResponse) {

            }
        })
    }

    private fun userChooseAPick(pickId : String,verifyAdId : Int,pickTitle: String) {
        selectedPickId = pickId
        selectedPickTitle = pickTitle
        if(!SharedPrefs.Utils.getFanId(context).isNullOrEmpty()) {

            createFanPick(pickId,selectedPickTitle)
        }
        getPublicAdsApi(verifyAdId)
    }

   private fun createFanPick(pickIdStr: String,pickTitle: String){
        ApiManager.createFanPick(prop.id,requireActivity(),object : SuccessFailureCallback{
            override fun onSuccess() {
            }
            override fun onFailure(messageResponse: MessageResponse) {
            }
        },pickIdStr,pickTitle)
    }

    private fun hideVerificationPopUp(){
        resetTimer()

        onsucessCallback.enableScroll()
        binding.verifyIdentityLayout.visibility = View.GONE
        adapter.setAnswerModeView(true)
        getPublicAdsApi(PostPickAdId)
        adapter.notifyDataSetChanged()
    }

    private fun switchToOtherModeToVerify() {
        if (isEmailMode) { // change to phone mode
            binding.countryCodePicker.visibility = View.VISIBLE

            binding.editTextCarrierNumber.setHint(R.string.edittext_hint_phone)
            binding.editTextCarrierNumber.setText("")
            binding.resendCodeText.setText("or use your email address")
            binding.editTextCarrierNumber.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        } else { // change to email view
            binding.countryCodePicker.visibility = View.GONE
            binding.editTextCarrierNumber.setHint(R.string.edittext_hint_email)
            binding.editTextCarrierNumber.setText("")
            binding.resendCodeText.setText("or use your phone number")
            binding.editTextCarrierNumber.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        }
        isEmailMode = !isEmailMode
    }




    private fun changeVerifyUI() {
        binding.verifyTitle.setText(R.string.enter_the_code_you_received)
        binding.countryCodePicker.visibility = View.GONE
        binding.editTextCarrierNumber.setHint("- - - - - -")
        binding.editTextCarrierNumber.setText("")
        binding.resendCodeText.setText("Didn't get the code? Click to resend it")
    }

    private fun setUpTimer() {
        binding.timerText.visibility = View.VISIBLE
        binding.timerText.setText(R.string.enter_the_code_within_2_minutes_to_verify_your_pick)
        binding.timerText.setTextColor(ContextCompat.getColor(requireActivity(),R.color.grey_light))
        binding.resendCodeText.visibility = View.VISIBLE
        timer = object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val seconds = (millisUntilFinished / 1000) % 60
                val minutes = (millisUntilFinished / (1000 * 60) % 60)
                //         val text = (minutes == 0) ? "" : minutes
                binding.timerText.setText("Enter the code within " + minutes.toInt() + " min " + seconds.toInt() + " sec to verify your pick")
            }

            override fun onFinish() {
                binding.editTextCarrierNumber.setText("")
                binding.timerText.visibility = View.GONE
                binding.timerText.setText("")
                binding.timerText.setTextColor(ContextCompat.getColor(requireActivity(),R.color.grey_light))
                binding.resendCodeText.visibility = View.VISIBLE
                binding.editTextCarrierNumber.setHint(R.string.edittext_hint_phone)
                binding.resendCodeText.setText(R.string.or_use_your_email_address)
                binding.verifyTitle.setText(R.string.verify_your_pick_to_see_results)
                isEmailMode = true
                enterCodeMode = false
                switchToOtherModeToVerify()

            }
        }
        timer.start()


        binding.bannerTitle.setOnClickListener {
            resetTimer()
        }
    }

   private fun resetTimer() {
        if (this::timer.isInitialized && timer != null) {
            timer.cancel()
        }

        binding.timerText.setText(R.string.enter_the_code_within_2_minutes_to_verify_your_pick)
    }

    private fun sendAuthenticateFanApi() {
        binding.progressBar.visibility = View.VISIBLE
        val authenticateBody = AuthenticateBody()

        Log.i(TAG, "sendVerifyFanApi: " + binding.countryCodePicker.isValidFullNumber)

        //   authenticateBody.code = Constants.Generic.TwillioCode
        if (isEmailMode) {
            if(emailForVerification.isNullOrEmpty() ) {
                Log.i(TAG, "sendAuthenticateFanApi: going to get data from edittext")
                var email = binding.editTextCarrierNumber.text.toString().trim()
                if (!Utilities.isValidEmail(email)) {
                    Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    return
                }
                emailForVerification = email
               
            }
            if(enterCodeMode) { // resending code
                authenticateBody.email = emailForVerification
            }else{
                authenticateBody.email = binding.editTextCarrierNumber.text.toString().trim()
            }
        } else {
            if(phoneNumberForVerification.isNullOrEmpty()) {
                phoneNumberForVerification = binding.countryCodePicker.fullNumberWithPlus
            //    phoneNumberForVerification = binding.editTextCarrierNumber.text.toString()
            }
            if(enterCodeMode) { // resending code
                authenticateBody.phoneNumber = phoneNumberForVerification
            }else{
               authenticateBody.phoneNumber =  binding.countryCodePicker.fullNumberWithPlus
             //   authenticateBody.phoneNumber =  binding.editTextCarrierNumber.text.toString()
            }
        }

        ApiManager.authenticateFan(requireActivity(), authenticateBody, object : SuccessFailureCallback {
                override fun onSuccess() {
                    if(!isAdded){
                        return
                    }
                    binding.progressBar.visibility = View.GONE
                    changeVerifyUI()
                    enterCodeMode = true
                    setUpTimer()
                }

                override fun onFailure(messageResponse: MessageResponse) {
                    binding.progressBar.visibility = View.GONE
                    if(!isAdded){
                        return
                    }
                    if(messageResponse != null){
                        activity?.runOnUiThread {
                            Toast.makeText(context,messageResponse.message,Toast.LENGTH_SHORT).show()
                        }
                    }

//                    changeVerifyUI()
//                    enterCodeMode = true
//                    setUpTimer()
                }
            })
    }

    private fun sendVerifyFanApi(code: String) {
        val verifyFanBody = VerifyFanBody()

        verifyFanBody.code = code

        if (isEmailMode) {

            verifyFanBody.email = emailForVerification
        } else {
            verifyFanBody.phoneNumber =phoneNumberForVerification
        }

        ApiManager.verifyFan(requireActivity(), verifyFanBody, object : SuccessFailureCallback {
            override fun onSuccess() {
                if(!isAdded){
                    return
                }
                binding.progressBar.visibility = View.GONE
                enterCodeMode = false
                activity?.runOnUiThread {
                    Toast.makeText(context,"Pick verified",Toast.LENGTH_SHORT).show()
                }

           //     SharedPrefs.Utils.saveIsUserVerified(requireActivity(),true)

                hideVerificationPopUp()
                createFanPick(selectedPickId,selectedPickTitle)
                getFanPicks()

                val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

                adapter.setAnswerModeView(true)
            }

            override fun onFailure(messageResponse: MessageResponse) {
                binding.progressBar.visibility = View.GONE
                if(!isAdded){
                    return
                }
                if(messageResponse != null){
                    activity?.runOnUiThread {
                        if(messageResponse.message.contains("invalid") || messageResponse.message.contains("Incorrect")){
                            resetTimer()
                            binding.timerText.setText(messageResponse.message)
                            binding.timerText.setTextColor(requireActivity().resources.getColor(R.color.red))
                        }
                        Toast.makeText(context,messageResponse.message,Toast.LENGTH_SHORT).show()
                    }
                }
//                    changeVerifyUI()
//                    enterCodeMode = true
//                    setUpTimer()
            }
        })
    }

    private fun getProps(){
        getPublicAdsApi(PrePickAdId)

      //  ApiManager.getProps(requireActivity())
    }

    private fun getPublicAdsApi( adId: Int) {
        ApiManager.getPublicAds(requireActivity(),adId,object : AdsCallback{
            override fun onSuccess(list: List<AdsResponseItem>?) {
                if(list != null && list.size>0){
                    var ad : AdsResponseItem  = list.get(0)
                    Log.i(TAG, "onSuccess: add to load " + ad)
                    Picasso.get().load(ad.ad_image).into(binding.adImage)


                }
            }

            override fun onFailure(messageResponse: MessageResponse) {

            }
        })
    }


}
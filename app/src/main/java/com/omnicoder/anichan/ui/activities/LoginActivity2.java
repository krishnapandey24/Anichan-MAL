package com.omnicoder.anichan.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.omnicoder.anichan.utils.Constants;
import com.omnicoder.anichan.utils.Extensions;
import com.omnicoder.anichan.viewModels.LoginViewModel;
import com.omnicoder.anichan.databinding.ActivityLoginBinding;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class LoginActivity2 extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    private Extensions extensions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel= new ViewModelProvider(this).get(LoginViewModel.class);
        extensions= Extensions.getInstance();
        binding.button.setOnClickListener(v -> {
//            viewModel.getAccessToken("def502007743032c59fae9797ef4ada5bc627624adc6bb52bdb0d08399bf8aea921f598e86185fb59613753ef4253620055d2fe6af8d76212fcd45603d3922e9bc8cd87128bf761c7395a182277e2c55b77800a073ce5b8d60f039e7b9d085f3c351af3952094f232bbf32a05ff4424e25e6e3208d065933966bfbf1acf1ef75083bb958c1e382d2eb2a9b93ae2e0c5e6aff6f490b2aa97ec29656953b8b49f14719465fa059dcd6d4cd9f804a59e12a497e8f3a75c80bd556287b05725f2a12cdc84786342356d35eb3e985427fac293a300707c2672b42b196fc82a524b3d229b0ee6194e48864b27699b83a7eb5f7a8bdc2e942a4e699f52d1b39d9c306a8c54d630e8e684f8de57b92349380bdcd5061197f4f4e1039ea06e38dbf78f6b4275ff31fce9d0e5ed36c4865fc8fe8e9ef7c3686ce7881839d8312a9ad1d84d9b0b52cb76acf8a80b4533c2ddbd3ad9a3701dc09ccffe8d99d363c10b566847d8f9d9a412e1381cdf8fc2b1666e9097029e59373a78a4a1c2fc3230049da33c6ae9a10a25006af32ff069f023b89b0e846d567c702f0ff590179953a9ae2c595bbe2f871b739e617c8ce1aee88bc695939bc5d9ff48bf7f112781e2e4e29834b4ee062aa369b9e8751c311c55eb4ae596c0b56ec855b7c7bacbfa339ae92bb4e36ec0c50712231156911");

            if(true){
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.loginUrl));
                try{
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }else{
                extensions.openCustomTab(viewModel.loginUrl,this);
            }



//            OkHttpClient client= new OkHttpClient();
//            RequestBody formBody= new FormBody.Builder()
//                    .add("client_id",Constants.CLIENT_ID)
//                    .add("code",Constants.CODE)
//                    .add("code_verifier",viewModel.codeVerified)
//                    .add("grant_type","authorization_")


        });
//       viewModel.getAccessToken("def502007743032c59fae9797ef4ada5bc627624adc6bb52bdb0d08399bf8aea921f598e86185fb59613753ef4253620055d2fe6af8d76212fcd45603d3922e9bc8cd87128bf761c7395a182277e2c55b77800a073ce5b8d60f039e7b9d085f3c351af3952094f232bbf32a05ff4424e25e6e3208d065933966bfbf1acf1ef75083bb958c1e382d2eb2a9b93ae2e0c5e6aff6f490b2aa97ec29656953b8b49f14719465fa059dcd6d4cd9f804a59e12a497e8f3a75c80bd556287b05725f2a12cdc84786342356d35eb3e985427fac293a300707c2672b42b196fc82a524b3d229b0ee6194e48864b27699b83a7eb5f7a8bdc2e942a4e699f52d1b39d9c306a8c54d630e8e684f8de57b92349380bdcd5061197f4f4e1039ea06e38dbf78f6b4275ff31fce9d0e5ed36c4865fc8fe8e9ef7c3686ce7881839d8312a9ad1d84d9b0b52cb76acf8a80b4533c2ddbd3ad9a3701dc09ccffe8d99d363c10b566847d8f9d9a412e1381cdf8fc2b1666e9097029e59373a78a4a1c2fc3230049da33c6ae9a10a25006af32ff069f023b89b0e846d567c702f0ff590179953a9ae2c595bbe2f871b739e617c8ce1aee88bc695939bc5d9ff48bf7f112781e2e4e29834b4ee062aa369b9e8751c311c55eb4ae596c0b56ec855b7c7bacbfa339ae92bb4e36ec0c50712231156911");





//        viewModel.accessToken.observe(this, accessToken -> {
//            Log.d("tagg","Acess token recieiv");
//            if(accessToken!=null){
//                SharedPreferences sharedPreferences= getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor= sharedPreferences.edit();
//                editor.putString("accessToken",accessToken.getAccess_token());
//                editor.putString("refreshToken",accessToken.getRefresh_token());
//                editor.putBoolean("userLogged",true);
//                editor.apply();
//                Intent intent=new Intent(LoginActivity2.this,MainActivity.class);
//                intent.putExtra("loggedIn",true);
//                startActivity(intent);
//                finish();
//            }else{
//                Toast.makeText(this,"Token null",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void parseIntentData(Uri uri){
        binding.message.setText("calling uri: "+uri.toString());
        String code= uri.getQueryParameter("code");
        String receivedState= uri.getQueryParameter("state");
        if(code != null && receivedState.equals(LoginViewModel.STATE)){
            viewModel.getAccessToken(code);
        }
    }

    private void check(){
        Intent rIntent= getIntent();
        Uri data = rIntent.getData();
        if(data!=null){
            binding.message.setText("data not null");
            if(data.toString().startsWith(Constants.ANICHAN_PAGE_LINK)){
                parseIntentData(data);
            }
        }else{
            binding.message.setText("data null");

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

//    private void takeIntent(){
//        Log.d("tagg","taking the intent");
//        Log.d("tagg"," si is +"+(getIntent().getData()==null));
//        Uri uri = getIntent().getData();
//        if(uri!=null){
//            Log.d("tagg","the uri receiived: "+uri.toString());
//            if (uri != null && uri.toString().startsWith(Constants.ANICHAN_PAGE_LINK)) {
//                // use the parameter your API exposes for the code (mostly it's "code")
//                String code = uri.getQueryParameter("code");
//                if (code != null) {
//                    parseIntentData(uri);
//                } else if (uri.getQueryParameter("error") != null) {
//                    Log.d("tagg","the error here is "+uri.getQueryParameter("error"));
//                    // show an error message here
//                }
//            }
//
//        }else{
//            Log.d("tagg","<B> It is null </B>");
//
//
//        }
//
//    }
//


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri= getIntent().getData();
        if (uri != null && uri.toString().startsWith(Constants.ANICHAN_PAGE_LINK)) {
            parseIntentData(uri);
        }

    }
}
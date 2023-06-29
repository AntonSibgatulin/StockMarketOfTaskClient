package ru.antonsibgatulin.stockmarketoftaskclient.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import ru.antonsibgatulin.stockmarketoftaskclient.AuthActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.HomeActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.MainActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.R;
import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;

public class SignInFragment extends Fragment {
    private View view;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextView signUpTextView;
    private AppCompatButton button;

    private ProgressDialog dilog;

    private TextInputLayout emailInputLayout,passwordInputLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_login, container, false);

        init();


        return view;

    }


    private void init() {
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);

        signUpTextView = view.findViewById(R.id.textSignUp);

        button = view.findViewById(R.id.button);
        emailInputLayout = view.findViewById(R.id.emailInputLayout);
        passwordInputLayout = view.findViewById(R.id.passwordInputLayout);

        dilog = new ProgressDialog(getContext());

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContiner, new SignUpFragment()).commit();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                valid();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                valid();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    private void login() {
        if(valid() == false){
            return;
        }
        dilog.setMessage("Login");
        dilog.setCancelable(false);
        dilog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response);
                    if (jsonObject.has("message")) {

                        if (jsonObject.getString("message").equals("Email not found")) {
                            //not valid data
                        }

                    } else {
                        Log.d("info",jsonObject.toString());
                        jsonObject = jsonObject.getJSONObject("token");

                        String token = jsonObject.getString("token");
                        Constant.saveData(getActivity(), "user", "token", token);

                        String userData = jsonObject.getJSONObject("user").toString();
                        Constant.saveData(getActivity(), "user", "userData", userData);

                        startActivity(new Intent(getActivity() , HomeActivity.class));
                        getActivity().finish();

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                dilog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                dilog.dismiss();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email", emailInput.getText().toString());
                params.put("password", passwordInput.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public boolean valid(){
        boolean flag = true;
        if(!emailInput.getText().toString().isEmpty() && !Constant.isValidEmail(emailInput.getText().toString())){
            flag = false;
            passwordInputLayout.setErrorEnabled(true);
            emailInputLayout.setError("Email is not valid");

        }else{
            emailInputLayout.setErrorEnabled(false);
        }

        if(passwordInput.getText().toString().isEmpty()==false && passwordInput.getText().toString().length()<8){
            flag = false;
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError("Password is not valid.");
        }
        else{
            passwordInputLayout.setErrorEnabled(false);
        }



        return flag;
    }

}

package ru.antonsibgatulin.stockmarketoftaskclient.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import ru.antonsibgatulin.stockmarketoftaskclient.AuthActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.HomeActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.R;
import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.user.User;

public class SignUpFragment extends Fragment {
    private View view;


    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText nameInput;
    private TextInputEditText surnameInput;


    private TextInputLayout emailInputLayout, passwordInputLayout, nameInputLayout, surnameInputLayout;


    private TextView singInTextView;
    private AppCompatButton button;

    private ProgressDialog dilog;

    public SignUpFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_register, container, false);

        init();


        return view;

    }

    private void init() {
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        nameInput = view.findViewById(R.id.nameInput);
        surnameInput = view.findViewById(R.id.surnameInput);

        button = view.findViewById(R.id.button);
        singInTextView = view.findViewById(R.id.textSignIn);

        dilog = new ProgressDialog(getContext());


        emailInputLayout = view.findViewById(R.id.emailInputLayout);
        passwordInputLayout = view.findViewById(R.id.passwordInputLayout);
        nameInputLayout = view.findViewById(R.id.nameInputLayout);
        surnameInputLayout = view.findViewById(R.id.surnameInputLayout);


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
        nameInput.addTextChangedListener(new TextWatcher() {
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
        surnameInput.addTextChangedListener(new TextWatcher() {
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


        singInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContiner, new SignInFragment()).commit();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singUp();
            }
        });
    }


    private void singUp() {
        if (valid() == false) {
            return;
        }
        dilog.setMessage("Registering");
        dilog.setCancelable(false);
        dilog.show();

        JSONObject json = new JSONObject();
        try {
            json.put("email", emailInput.getText().toString());
            json.put("password", passwordInput.getText().toString());
            JSONObject profile = new JSONObject();
            profile.put("name", nameInput.getText().toString());
            profile.put("surname", surnameInput.getText().toString());
            json.put("profile", profile);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.REG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response).getJSONObject("token");
                    String token = jsonObject.getString("token");
                    Constant.saveData(getActivity(), "user", "token", token);
                    String userData = jsonObject.getJSONObject("user").toString();
                    Constant.saveData(getActivity(), "user", "userData", userData);

                    startActivity(new Intent(getActivity() , HomeActivity.class));
                    getActivity().finish();
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

            @Override
            public byte[] getBody() throws AuthFailureError {
                return json.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    public boolean valid() {
        boolean flag = true;
        if (emailInput.getText().toString().isEmpty() == false && !Constant.isValidEmail(emailInput.getText().toString())) {
            flag = false;
            emailInputLayout.setErrorEnabled(true);
            emailInputLayout.setError("Email is not valid");

        } else {
            emailInputLayout.setErrorEnabled(false);
        }

        if (passwordInput.getText().toString().isEmpty() == false && passwordInput.getText().toString().length() < 8) {
            flag = false;
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError("Password is not valid. Min number of characters 8");
        } else {
            passwordInputLayout.setErrorEnabled(false);
        }

        if (!nameInput.getText().toString().isEmpty() && nameInput.getText().toString().length() < 2) {
            flag = false;
            nameInputLayout.setErrorEnabled(true);
            nameInputLayout.setError("Name is not valid");
        } else {
            nameInputLayout.setErrorEnabled(false);
        }


        if (!surnameInput.getText().toString().isEmpty() && surnameInput.getText().toString().length() < 2) {
            flag = false;
            surnameInputLayout.setErrorEnabled(true);
            surnameInputLayout.setError("Name is not valid");
        } else {
            surnameInputLayout.setErrorEnabled(false);
        }


        return flag;
    }


}

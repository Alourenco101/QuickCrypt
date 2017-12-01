package com.socialgamingfun.quickcrypt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.SimpleShowcaseEventListener;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.appinvite.AppInviteInvitation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    EditText inputField;
    EditText resultsField;
    EditText numberInputField;
    ImageButton lockButton;
    ImageButton unlockButton;
    ImageButton copyButton;
    ImageButton shareButton;
    ImageView backgroundLogo;
    Boolean cont;
    int tutorialChecker[];
    ConstraintLayout constraintLayout;
    private static final int REQUEST_INVITE = 0;
    private AdView mAdView;
    ArrayList<Character> alpha = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ', '-', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '@', '#', '$', '&', '*', '+', '=', '(', ')', '_', '"', ':', ';', '/', '?', '!', '%', '.', '[', ']', '{', '}', '<', '>', ',', '^', '`', '~', '\''));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-5749066715551466/2351608785");
        inputField = (EditText) findViewById(R.id.inputField);
        resultsField = (EditText) findViewById(R.id.resultsField);
        resultsField.setFocusable(false);
        resultsField.setClickable(true);
        numberInputField = (EditText) findViewById(R.id.numberInputField);

        cont=true;
        tutorialChecker= new int[]{0,0,0,0,0,0,0};

        lockButton = (ImageButton) findViewById(R.id.lockButton);
        unlockButton = (ImageButton) findViewById(R.id.unlockButton);
        copyButton = (ImageButton) findViewById(R.id.copyButton);
        shareButton = (ImageButton) findViewById(R.id.shareButton);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        backgroundLogo = (ImageView) findViewById(R.id.backgroundLogo);
        backgroundLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }
        });


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }


    public void encrypt(View view) {
        if (checkValues() && checkNumber()) {
            Encrypt encrypt = new Encrypt(inputField.getText().toString(), Integer.valueOf(numberInputField.getText().toString()), inputField.getText().length());
            encrypt.encrypt();
            encrypt.oddShift();
            encrypt.evenShift();
            resultsField.setText(encrypt.text);
        }

    }

    public void decrypt(View view) {
        if (checkValues() && checkNumber()) {
            Decrypt decrypt = new Decrypt(inputField.getText().toString(), Integer.valueOf(numberInputField.getText().toString()), inputField.getText().length());
            decrypt.oddShiftDecrypt();
            decrypt.evenShiftDecrypt();
            decrypt.decrypt();
            resultsField.setText(decrypt.text);
        }
    }

    public boolean checkValues() {

        if (numberInputField.getText().length() == 0 || numberInputField.getText().length() >= 4) {
            displayError("Error with number input field");
            return false;
        }
        if (inputField.getText().length() == 0) {
            displayError("No input :(");
            return false;
        }
        String text = inputField.getText().toString();
        for (int i = 0; i < text.length(); i++) {
            if (!alpha.contains(text.charAt(i))) {
                displayError("Special Characters not supported in use :/");
                return false;
            }
        }
        return true;
    }

    public boolean checkNumber() {
        if ((numberInputField.getText().length() != 0)) {
            String text = numberInputField.getText().toString();
            try {
                int num = Integer.parseInt(text);
                if (num > 100) {
                    displayError("Error, number to high");
                    return false;
                }
            } catch (NumberFormatException e) {
                displayError("Error, number not inputed");
                return false;
            }
            return true;
        }
        return false;
    }

    public void copyButton(View view) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(resultsField.getText().toString());
            displayError("Copied Text");
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("CopyButton Label", resultsField.getText().toString());
            clipboard.setPrimaryClip(clip);
            displayError("Copied Text");

        }
    }

    public void shareButton(View view) {
        Intent intent = new AppInviteInvitation.IntentBuilder("Share QuickCrypt")
                .setMessage("Use QuickCrypt to encrypt or decrypt messages quickly! ")
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    public void displayError(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void displayHowItWorks(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        showcase(0);
    }

    public void updateArray(int x){
        tutorialChecker[x]=1;
    }

    private void showcase(int x) {
        if(x==0 && tutorialChecker[x]==0){
            updateArray(x);
            Target target = new ViewTarget(R.id.yourText, MainActivity.this);
            ShowcaseView a=  new ShowcaseView.Builder(this)
                    .setContentTitle(R.string.inputFieldTitle)
                    .setContentText(R.string.inputFieldDetail)
                    .setTarget(target)
                    .setStyle(R.style.CustomShowcaseTheme)
                    .setShowcaseEventListener(
                            new SimpleShowcaseEventListener() {
                                @Override
                                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                    showcaseView.hideButton();
                                    showcase(1);
                                }
                            }
                    )
                    .build();;
        }
        else if(x==1 && tutorialChecker[x]==0){
            updateArray(x);
            Target target = new ViewTarget(R.id.numberInputField, MainActivity.this);
            new ShowcaseView.Builder(this)
                    .setContentTitle(R.string.numberFieldTitle)
                    .setContentText(R.string.numberFieldDetail)
                    .setTarget(target)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setShowcaseEventListener(
                            new SimpleShowcaseEventListener() {
                                @Override
                                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                    showcaseView.hideButton();
                                    showcase(2);
                                }
                            }
                    )
                    .build();
        }
        else if(x==2 && tutorialChecker[x]==0){
            updateArray(x);
            Target target = new ViewTarget(R.id.lockButton, MainActivity.this);
            new ShowcaseView.Builder(this)
                    .setContentTitle(R.string.encryptButtonTitle)
                    .setContentText(R.string.encryptButtonDetail)
                    .setTarget(target)
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setShowcaseEventListener(
                            new SimpleShowcaseEventListener() {
                                @Override
                                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                    showcaseView.hideButton();
                                    showcase(3);
                                }
                            }
                    )
                    .build();
        }

        else if(x==3 && tutorialChecker[x]==0){
            updateArray(x);
            Target target = new ViewTarget(R.id.unlockButton, MainActivity.this);
            new ShowcaseView.Builder(this)
                    .setContentTitle(R.string.decryptButtonTitle)
                    .setContentText(R.string.decryptButtonDetail)
                    .setTarget(target)
                    .setStyle(R.style.CustomShowcaseTheme4)
                    .setShowcaseEventListener(
                            new SimpleShowcaseEventListener() {
                                @Override
                                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                    showcaseView.hideButton();
                                    showcase(4);
                                }
                            }
                    )
                    .build();
        }

        else if(x==4  && tutorialChecker[x]==0){
            updateArray(x);
            Target target = new ViewTarget(R.id.results, MainActivity.this);
            new ShowcaseView.Builder(this)
                    .setContentTitle(R.string.resultFieldTitle)
                    .setContentText(R.string.resultFieldDetail)
                    .setTarget(target)
                    .setStyle(R.style.CustomShowcaseTheme)
                    .setShowcaseEventListener(
                            new SimpleShowcaseEventListener() {
                                @Override
                                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                    showcaseView.hideButton();
                                    showcase(5);
                                }
                            }
                    )
                    .build();
        }

        else if(x==5 && tutorialChecker[x]==0){
            updateArray(x);
            Target target = new ViewTarget(R.id.copyButton, MainActivity.this);
            new ShowcaseView.Builder(this)
                    .setContentTitle(R.string.copyTitle)
                    .setContentText(R.string.copyDetail)
                    .setTarget(target)
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setShowcaseEventListener(
                            new SimpleShowcaseEventListener() {
                                @Override
                                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                    showcaseView.hideButton();
                                    showcase(6);
                                }
                            }
                    )
                    .build();
        }
        else if(x==6 && tutorialChecker[x]==0){
            updateArray(x);
            Target target = new ViewTarget(R.id.shareButton, MainActivity.this);
            new ShowcaseView.Builder(this)
                    .setContentTitle(R.string.shareButton)
                    .setContentText(R.string.shareButtonDetail)
                    .setTarget(target)
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setShowcaseEventListener(
                            new SimpleShowcaseEventListener() {
                                @Override
                                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                                    showcaseView.hideButton();
                                    showcase(7);
                                    tutorialChecker= new int[]{0,0,0,0,0,0,0};
                                }
                            }
                    )
                    .build();
        }

    }

}
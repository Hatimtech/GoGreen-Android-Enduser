package com.gogreen.main.Interfaces;

import com.sinch.verification.InitiationResult;
import com.sinch.verification.InvalidInputException;
import com.sinch.verification.ServiceErrorException;
import com.sinch.verification.VerificationListener;

public class MyVerficationListener implements VerificationListener {




    @Override
        public void onInitiated(InitiationResult initiationResult) {

        }

        @Override
        public void onInitiationFailed(Exception e) {
            if (e instanceof InvalidInputException) {

            } else if (e instanceof ServiceErrorException) {

            } else {

            }
        }

        @Override
        public void onVerified() {

        }

        @Override
        public void onVerificationFailed(Exception e) {

        }

        @Override
        public void onVerificationFallback() {

        }
    }


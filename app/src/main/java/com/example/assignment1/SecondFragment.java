package com.example.assignment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.assignment1.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    //local variables to handle various application elements
    private TextView monthlyPaymentsView;
    private EditText downPayment;
    private EditText interestRate;
    private EditText amortizationRate;
    private Double down_payment;
    private Double interest_rate;
    private Double amortization_rate;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //gets the reference of each EditText view field
        downPayment = (EditText) view.findViewById(R.id.principalAmount);
        interestRate = (EditText) view.findViewById(R.id.interestRate);
        amortizationRate = (EditText) view.findViewById(R.id.amortizationPeriod);

        //gets the reference of the TextView view field
        monthlyPaymentsView = (TextView) view.findViewById(R.id.textview_result);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        //listens for "Calculate" button press
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parses values to Double type
                down_payment = Double.parseDouble(downPayment.getText().toString());
                interest_rate = Double.parseDouble(interestRate.getText().toString());
                amortization_rate = Double.parseDouble(amortizationRate.getText().toString());

                //calls EMI method and passes the variables necessary for calculations and outputs results
                monthlyPaymentsView.setText("$" + emiCalculate(down_payment, interest_rate, amortization_rate).toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //calculates the EMI and returns value
    private Double emiCalculate(Double down_payment, Double interest_rate, Double amortization_rate) {
        Double emi;

        //EMI formula based on monthly payments
        Double repeatCalc = Math.pow((1 + ((interest_rate / 100) / 12)), (amortization_rate * 12));
        emi = down_payment * ((interest_rate / 100) / 12) * (repeatCalc / (repeatCalc - 1));

        //rounds and returns value to 2 decimal places
        return Math.round(emi * 100.0) / 100.0;
    }
}
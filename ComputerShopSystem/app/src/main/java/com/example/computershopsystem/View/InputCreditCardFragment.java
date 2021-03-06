package com.example.computershopsystem.View;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.InputCreditCardFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InputCreditCardFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    InputCreditCardFragmentBinding binding;
    EditText numberCard;
    EditText money;
    TextView expirationDate;
    EditText holderCard;

    // khai bao bien de lay list credit card so sanh id
    List<CreditCard> creditCardList;
    String id;
    String keyCreditCard;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Boolean check;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = InputCreditCardFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        id = null;
        keyCreditCard = null;
        CreditCard creditCard = new CreditCard();

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        // hung du lieu tu binding
        numberCard = binding.edCardNumberAdd;
        money = binding.edMoneyCardAdd;
        expirationDate = binding.edExpirationCardAdd;
        holderCard = binding.edCardHolderAdd;

        // set Input Type cua number card v?? money la so
        numberCard.setInputType(InputType.TYPE_CLASS_NUMBER);
        money.setInputType(InputType.TYPE_CLASS_NUMBER);
        if (firebaseUser!=null){
            // khai bao bien share preferences de lay du lieu
            sharedpreferences = getContext().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();

            // lay id Card tu Bundle
            Bundle bundle = this.getArguments();
            if(bundle != null) {
                id = bundle.getString("IdCard");
            }

            // get data tu id vua nhan
            Query data = FirebaseDatabase.getInstance().getReference("Customer").child(firebaseUser.getUid()).child("cardList").orderByChild("id").equalTo(id);
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // neu co ket qua hien thi no ra man hinh
                        for (DataSnapshot shot : snapshot.getChildren()) {
                            keyCreditCard=shot.getKey();
                            CreditCard creditCard = shot.getValue(CreditCard.class);
                            numberCard.setText(creditCard.getCardNumber());
                            money.setText(creditCard.getMoney());
                            expirationDate.setText(creditCard.getExpirationDate());
                            holderCard.setText(creditCard.getCardHolder());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

        // neu co id thay doi trang thanh trang update
        if(!(id == null)){
            binding.tvNameTop12.setText("Update Card");
            binding.btnAddCard.setText("Save");
        }

        // bat du lieu nhap vao number card
        numberCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // du lieu nhap vao khong duoc rong, khong dc qua 12 chu so
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getList();
                numberCard.setBackground(getActivity().getDrawable(R.drawable.border));
                if(s.toString().equals("")){
                    numberCard.setError("Please enter Number Card !");
                    numberCard.setBackground(getActivity().getDrawable(R.drawable.border_red));
                }
                if(!(s.length()== 12)){
                    numberCard.setError("Number Card must have 12 numbers !");
                    numberCard.setBackground(getActivity().getDrawable(R.drawable.border_red));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // bat du lieu nhap vao money
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // du lieu so nhap vao khong dc rong v?? phai l??n hon 0
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                money.setBackground(getActivity().getDrawable(R.drawable.border));
                if(s.toString().equals("")){
                    money.setError("Please enter Money !");
                    money.setBackground(getActivity().getDrawable(R.drawable.border_red));
                }
                try {
                    if(Integer.parseInt(s.toString()) <= 0){
                        money.setError("Money must greater than $0 !");
                        money.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    }
                }catch (Exception ex){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // set chon calendar cho Date
        expirationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                //getting current day,month and year.
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        expirationDate.setText( (monthOfYear + 1)+ "/" +dayOfMonth  + "/" + year);
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        dateSetListener, year, month, day);

                datePickerDialog.show();
            }
        });

        // bat du lieu cho holder card
        holderCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // du lieu nhap vao khong dc rong
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                money.setBackground(getActivity().getDrawable(R.drawable.border));
                if(s.toString().equals("")){
                    holderCard.setError("Please enter Holder Card !");
                    holderCard.setBackground(getActivity().getDrawable(R.drawable.border_red));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // set even cho nut add
        binding.btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // tao mot bien check de check du lieu nhap vao
                check = true;

                // neu number card rong thi check = false
                String cardNumber = String.valueOf(numberCard.getText());
                if(cardNumber.equals("")){
                    numberCard.setError("Please enter Number Card !");
                    numberCard.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    check = false;
                }

                // neu expiration date rong thi bat ng dung chon lai va check = false
                String expiration = String.valueOf(expirationDate.getText());
                if(String.valueOf(expirationDate.getText()).equals("")){
                    expirationDate.setError("Please enter Expiration Date !");
                    expirationDate.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    check = false;
                }

                // neu card holder rong thi bat ng dung nhap lai va check = false
                String cardHolder = String.valueOf(holderCard.getText());
                if(cardHolder.equals("")){
                    holderCard.setError("Please enter Holder card !");
                    holderCard.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    check = false;
                }

                // neu tien rong thi bat ng dung nhap lai va check = false
                String cardMoney = String.valueOf(money.getText());
                if(cardMoney.equals("")){
                    money.setError("Please enter Money !");
                    money.setBackground(getActivity().getDrawable(R.drawable.border_red));

                    check = false;
                }

                // neu check == true va number card khong b??? trung thi add du lieu vao fire base
                if(check && checkDuplicate(cardNumber)) {
                    String idCard = id;
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(firebaseUser.getUid()).child("cardList");
                    if((id == null)) {
                        idCard = databaseReference.push().getKey();
                    }

                    CreditCard creditCard = new CreditCard(idCard, String.valueOf(Integer.parseInt(cardMoney.toString())), cardNumber, expiration, cardHolder, new Date(), new Date());
                    databaseReference.child(idCard).setValue(creditCard);

                    // chuyen trang den credit card fragment
                    CreditCardFragment fragment = new CreditCardFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                    fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                            android.R.animator.fade_out);
                    fragTransaction.addToBackStack(null);
                    fragTransaction.replace(R.id.fl_wrapper, fragment);
                    fragTransaction.commit();
                }
            }
        });

        return  view;
    }

    // lay toan bo card trong firebase
    void getList(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(firebaseUser.getUid()).child("cardList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                creditCardList = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot shot : snapshot.getChildren()) {
                        keyCreditCard=shot.getKey();
                        CreditCard creditCard = shot.getValue(CreditCard.class);
                        creditCardList.add(creditCard);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    // check xem number card co bi trung khong
    Boolean checkDuplicate(String number){
        Boolean isUpdate = false;
        for(CreditCard item : creditCardList){
            if(id!=null && (id.equals(item.getId()))){
                isUpdate = true;
            }
            if(item.getCardNumber().trim().equals(number.trim())&& !isUpdate){
                numberCard.setError("Your number card had existed !");
                numberCard.setBackground(getActivity().getDrawable(R.drawable.border_red));
                return false;
            }
        }
        return true;
    }
}
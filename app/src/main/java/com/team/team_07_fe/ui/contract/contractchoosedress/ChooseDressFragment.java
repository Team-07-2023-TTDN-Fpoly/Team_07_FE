package com.team.team_07_fe.ui.contract.contractchoosedress;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.ContractDetail;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.ui.contract.ContractHandleViewModel;
import com.team.team_07_fe.ui.contract.ContractRequestViewModel;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.viewmodels.DressViewModel;

import java.util.Calendar;
import java.util.List;

public class ChooseDressFragment extends Fragment {
    private TextInputLayout layout_input_choose_dress,layout_input_price,layout_date_return,layout_date_rental;
    private AutoCompleteTextView dropdown_dress;
    private AppCompatButton btn_add_item;
    private ImageView image_choose_dress;

    private DressViewModel dressViewModel;
    private ContractHandleViewModel contractHandleViewModel;
    private ContractRequestViewModel contractRequestViewModel;
    private ArrayAdapter<Dress> dressArrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contract_choose_dress, container, false);
        mapping(view);
        dressViewModel = new ViewModelProvider(requireActivity()).get(DressViewModel.class);
        contractHandleViewModel = new ViewModelProvider(requireActivity()).get(ContractHandleViewModel.class);
        contractRequestViewModel = new ViewModelProvider(requireActivity()).get(ContractRequestViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dressViewModel.getAllDress(null);

        observeViewModel();
        handleClick();
    }

    /**
     * Kiểm tra dữ liệu từ viewmodel
     */
    private void observeViewModel(){
        dressViewModel.getListDress().observe(getViewLifecycleOwner(),dresses -> {
            if(dresses!=null){
                setAdapter(dresses);
            }
        });

        contractHandleViewModel.getSelectDress().observe(getViewLifecycleOwner(),dress -> {
            if(dress!=null){
                Log.i("IMAGE",dress.getImage());
                Glide.with(this)
                        .load(dress.getImage())
                        .into(image_choose_dress);
                layout_input_price.getEditText().setText(FormatHelper.convertPriceToString(dress.getDress_price()));
            }
        });


        contractHandleViewModel.getAddStatus().observe(getViewLifecycleOwner(),aBoolean -> {
            if(aBoolean!=null){
                ContractDetail newContractDetail = contractHandleViewModel.getSelectContractDetail();
                if(aBoolean){
                    Toast.makeText(requireContext(), "Thêm áo thành công!", Toast.LENGTH_SHORT).show();
                    if(newContractDetail!=null){
                        contractRequestViewModel.addDressToListContractDetail(newContractDetail);
                        NavHostFragment.findNavController(ChooseDressFragment.this).popBackStack();
                    }
                }else{
                    Toast.makeText(requireContext(), "Không thể thêm áo!", Toast.LENGTH_SHORT).show();
                }
                contractHandleViewModel.resetChooseDress();
            }});
    }
    /**
     * Xử lý sự kiện click
     */
    private void handleClick(){
        dropdown_dress.setOnItemClickListener((adapterView, view, i, l) -> {
            contractHandleViewModel.setSelectDress((Dress)adapterView.getItemAtPosition(i));
        });
        
        btn_add_item.setOnClickListener(view -> {
            if(validateInput()){
                String returnDate = layout_date_return.getEditText().getText().toString().trim();
                String rentalDate = layout_date_rental.getEditText().getText().toString().trim();
                ContractDetail newContractDetail =
                        new ContractDetail(contractHandleViewModel.getSelectDress().getValue()
                        ,FormatHelper.convertStringtoDate(rentalDate)
                        ,FormatHelper.convertStringtoDate(returnDate));
                contractHandleViewModel.setSelectContractDetail(newContractDetail);
                contractHandleViewModel.addDressToListContractDetail(newContractDetail);

                Log.i("LIST DRESS",contractHandleViewModel.getListContractDetail().toString());
            }
        });

        //lựa chọn ngày thuê
        layout_date_rental.getEditText().setOnClickListener(v->{
            showDialogChooseDate(v,layout_date_rental);
        });
        //lựa chon ngày trả
        layout_date_return.getEditText().setOnClickListener(v->{
            showDialogChooseDate(v,layout_date_return);
        });
    }

    /**
     * Lựa chọn ngày
     * @param view -
     * @param textInputLayout - input nhận thông tin ngày
     */
    private void showDialogChooseDate(View view,TextInputLayout textInputLayout){
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // Xử lý ngày được chọn ở đây
                            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            // Ví dụ: set text cho một TextView
                            textInputLayout.getEditText().setText(selectedDate);
                        }
                    },
                    year, month, day);
            datePickerDialog.show();
    }
    /**
     * Kiểm tra dữ liệu trước khi click button thêm mới
     * @return boolean
     * - true : không có lõi
     * - false : cần kiểm tra lại dữ liệu
     */
    private boolean validateInput(){
        boolean isValid = true;
        if(contractHandleViewModel.getSelectDress().getValue() == null){
            layout_input_choose_dress.setError("Vui lòng chọn áo cưới!");
            isValid = false;
            layout_input_choose_dress.getEditText().requestFocus();
        }else{
            layout_input_choose_dress.setError(null);
        }
        String returnDate = layout_date_return.getEditText().getText().toString().trim();
        String rentalDate = layout_date_rental.getEditText().getText().toString().trim();

        if(rentalDate.isEmpty()){
            layout_date_rental.setError("Chọn lại!");
            layout_date_rental.getEditText().requestFocus();
            isValid = false;
        }else if(returnDate.isEmpty()){
            layout_date_return.setError("Chọn lại!");
            layout_date_return.getEditText().requestFocus();
            isValid = false;
        }else if(FormatHelper.convertStringtoDate(returnDate).compareTo(FormatHelper.convertStringtoDate(rentalDate))<=0){
            layout_date_return.setError("Chọn lại!");
            layout_date_return.getEditText().requestFocus();
            isValid = false;
        }else{
            layout_date_rental.setError(null);
            layout_date_return.setError(null);
        }

        return isValid;
    }
    /**
     * Gán lại giá trị cho adapter dress
     */
    private void setAdapter(List<Dress> dresses){
        dressArrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line,dresses);
        dropdown_dress.setAdapter(dressArrayAdapter);
    }
    /**
     * Mapping dữ liệu
     * @param view  - layout
     */
    private void mapping(View view){
        layout_input_choose_dress = view.findViewById(R.id.layout_input_choose_dress);
        layout_input_price = view.findViewById(R.id.layout_input_price);
        layout_date_rental = view.findViewById(R.id.layout_date_rental);
        layout_date_return = view.findViewById(R.id.layout_date_return);
        btn_add_item = view.findViewById(R.id.btn_add_item);
        dropdown_dress = view.findViewById(R.id.dropdown_dress);
        image_choose_dress = view.findViewById(R.id.image_choose_dress);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)requireActivity()).hiddenBottomBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) requireActivity()).showBottomBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Glide.with(this).load(R.drawable.ic_image).into(image_choose_dress);
        contractHandleViewModel.resetChooseDress();
    }
}
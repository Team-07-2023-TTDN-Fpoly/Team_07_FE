package com.team.team_07_fe.fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.DetailStatistics;
import com.team.team_07_fe.request.CustomerRequest;
import com.team.team_07_fe.request.DetailStatisticsRequest;
import com.team.team_07_fe.request.EmployeeRequest;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.DetailStatisticsViewModel;

import java.util.Calendar;
import java.util.Date;

public class CreateDetailFragment extends Fragment {
    private TextInputLayout create_detail_input_date,
            create_detail_input_name, create_detail_input_money, create_detail_input_text;

    private AppCompatButton btn_add_detail;
    private LoadingDialog loadingDialog;
    private DetailStatisticsViewModel mViewDetailStatistic;
    private DetailStatisticsRequest detailStatisticsRequest;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewDetailStatistic = new ViewModelProvider(requireActivity()).get(DetailStatisticsViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        View view = inflater.inflate(R.layout.fragment_create_detail, container, false);
        mapping(view);
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_add_detail.setOnClickListener(this::AddDetail);
        create_detail_input_date.getEditText().setOnClickListener(this::dateforcreatedetail);
        observeData();
    }
    public void observeData(){
        mViewDetailStatistic.getDataInput().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                NavHostFragment.findNavController(this).popBackStack();
                Toast.makeText(requireContext(), "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
                mViewDetailStatistic.setDataInput(null);
            }
        });
        mViewDetailStatistic.getErrorMessage().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void AddDetail(View view) {
        String date = create_detail_input_date.getEditText().getText().toString().trim();
        String name = create_detail_input_name.getEditText().getText().toString().trim();
        String money = create_detail_input_money.getEditText().getText().toString().trim();
        String text = create_detail_input_text.getEditText().getText().toString().trim();


//        if(valueInput(date,name,money,text)){
//            loadingDialog.show();
//            Date dateforcreatedetail = null;
//            if (!TextUtils.isEmpty(dateforcreatedetail)) {
//                dateforcreatedetail = FormatHelper.convertStringtoDate(dateforcreatedetail);
//            }
//            mViewDetailStatistic.addDetailStatistics(new DetailStatisticsRequest(new Date(),name, Long.parseLong(money),text));
//            Toast.makeText(requireContext(), "Thêm mới khoản chi thành công!", Toast.LENGTH_SHORT).show();
//            requireActivity().onBackPressed();
//        }
//    }
        if (valueInput(date, name, money, text)) {
            loadingDialog.show();
            Log.i("DATA DATE", FormatHelper.convertStringtoMonth(date) +"");
            DetailStatisticsRequest detailStatisticsRequest = new DetailStatisticsRequest(new Date(), name, Long.parseLong(money), text);
            confirmCreateDetail(detailStatisticsRequest);
        }
    }

         private void confirmCreateDetail(DetailStatisticsRequest detailStatisticsRequest){
        mViewDetailStatistic.addDetailStatistics(detailStatisticsRequest);
    }
    private void dateforcreatedetail(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        DatePickerDialog monthYearPickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear,
                                          int selectedMonth, int dayOfMonth) {
                        String selectedMonthYear = (selectedMonth + 1) + "/" + selectedYear;
                        create_detail_input_date.getEditText().setText(selectedMonthYear);

                    }
                } ,year, month, 1);
        // 1: Ngày mặc định, chúng ta chỉ quan tâm đến tháng và năm
        // Chỉ hiển thị tháng và năm
        monthYearPickerDialog.show();
    }

    ///////
    private boolean valueInput(String date, String name,
                               String money, String text) {
        boolean isvalid = true;
        if (TextUtils.isEmpty(date)) {
            create_detail_input_date.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            create_detail_input_date.setError(null);
        }
        if (TextUtils.isEmpty(name)) {
            create_detail_input_name.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            create_detail_input_name.setError(null);
        }
        if (TextUtils.isEmpty(money)) {
            create_detail_input_money.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            create_detail_input_money.setError(null);
        }
        if (TextUtils.isEmpty(text)) {
            create_detail_input_text.setError(null);
            isvalid = false;
        } else {
            create_detail_input_text.setError(null);
        }
        return isvalid;
    }
    private void mapping(View view){
        create_detail_input_date = view.findViewById(R.id.create_detail_input_date);
        create_detail_input_name = view.findViewById(R.id.create_detail_input_name);
        create_detail_input_money = view.findViewById(R.id.create_detai_input_money);
        create_detail_input_text = view.findViewById(R.id.create_detail_input_text);
        btn_add_detail = view.findViewById(R.id.btn_add_detail);
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

}
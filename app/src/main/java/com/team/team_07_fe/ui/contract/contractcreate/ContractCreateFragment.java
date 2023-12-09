package com.team.team_07_fe.ui.contract.contractcreate;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.adapter.ContractDetailAdapter;
import com.team.team_07_fe.models.ContractDetail;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.Employee;
import com.team.team_07_fe.request.ContractDetailRequest;
import com.team.team_07_fe.request.ContractRequest;
import com.team.team_07_fe.ui.contract.ContractHandleViewModel;
import com.team.team_07_fe.ui.contract.ContractRequestViewModel;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.ContractViewModel;
import com.team.team_07_fe.viewmodels.CustomerViewModel;

import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ContractCreateFragment extends Fragment {
    private TextInputLayout layout_input_customer,layout_input_customer_name,layout_input_customer_phone,
    layout_input_createAt,layout_input_endAt,layout_input_total,layout_input_prepay,layout_input_discount;
    private TextView tv_choose_dress;
    private TextView tv_employee_name;
    private AutoCompleteTextView dropdown_customer;
    private RecyclerView recyclerView;
    private AppCompatButton btn_create_item;

    private CustomerViewModel customerViewModel;
    private ContractHandleViewModel contractHandleViewModel;
    private ContractRequestViewModel contractRequestViewModel;
    private ContractViewModel contractViewModel;

    private ArrayAdapter<Customer> customerArrayAdapter;
    private ContractDetailAdapter contractDetailAdapter;
    private List<ContractDetail> listContractDetail;
    private Employee employee;

    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contract_create, container, false);
        mapping(view);
        loadingDialog = new LoadingDialog(requireActivity());
        customerViewModel = new ViewModelProvider(requireActivity()).get(CustomerViewModel.class);
        contractHandleViewModel = new ViewModelProvider(requireActivity()).get(ContractHandleViewModel.class);
        contractRequestViewModel = new ViewModelProvider(requireActivity()).get(ContractRequestViewModel.class);
        contractViewModel = new ViewModelProvider(requireActivity()).get(ContractViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customerViewModel.getAllCustomer(null);
        observeDataCustomer();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyNS", Context.MODE_PRIVATE);
        String employeeData = sharedPreferences.getString("employee",null);
        Gson gson = new Gson();
        employee = gson.fromJson(employeeData,Employee.class);

        handleClick();
        observeDataContract();
        dataInput();

        contractViewModel.getDataInput().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireActivity(), "Thêm mới thành công!", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(ContractCreateFragment.this).popBackStack();
                contractViewModel.setDataInput(null);
                contractHandleViewModel.resetAllData();
                contractRequestViewModel.resetAllData();
            }
        });
        contractViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s -> {
            if(s!=null && !s.isEmpty()){
                loadingDialog.dismiss();
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                contractViewModel.setErrorMessage(null);
            }
        });
    }
    /**
     * Sự kiện các thành phần input.
     * - Tiền giảm giá - tiền trả trước
     */
    private void dataInput(){
        tv_employee_name.setText(employee.getEmp_name());
        layout_input_prepay.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    long value = Long.parseLong(editable.toString());
                    contractHandleViewModel.setPrepay(value);
                }catch (NumberFormatException ignored){
                    contractHandleViewModel.setPrepay(0L);
                }
            }
        });
        layout_input_discount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    long value = Long.parseLong(editable.toString());
                    contractHandleViewModel.setDiscount(value);
                }catch (NumberFormatException ignored){
                    contractHandleViewModel.setDiscount(0L);
                }
            }
        });
    }
    /**
     * Xử lý sự kiện của các phần nút nhấn
     * - "Thêm" chuyển sang lựa chọn áo cưới cho hợp đồng
     * - Lựa chọn khách hàng cho hợp đồng
     * - Button "Thêm mới" - kiểm tra dữ liệu trước khi thêm
     *    + false : hiển thị thông báo lỗi
     *    + true : thêm mới hợp đồng -> chuyển sang màn hình danh sách hợp đồng
     */
    private void handleClick(){
        tv_choose_dress.setOnClickListener(view -> {
            NavHostFragment.findNavController(ContractCreateFragment.this).navigate(R.id.action_navigation_contract_create_to_navigation_contract_choose_dress);
        });
        //Lựa chọn cho customer
        dropdown_customer.setOnItemClickListener((adapterView, view1, i, l) -> {
            contractHandleViewModel.setSelectCustomer(customerArrayAdapter.getItem(i));
        });

        btn_create_item.setOnClickListener(view -> {
            Log.i("DISCOUNT PREPAY", contractHandleViewModel.getDiscount().getValue()+" - "+contractHandleViewModel.getPrepay().getValue());
            if(validateInput()){
                loadingDialog.show();
                Customer customer = contractHandleViewModel.getSelectCustomer().getValue();
                Date date_create = FormatHelper.convertStringtoDate(contractHandleViewModel.getDateCreateAt().getValue());
                Date date_endAt = FormatHelper.convertStringtoDate(contractHandleViewModel.getDateEndAt().getValue());
                List<ContractDetailRequest> contractRequests = contractRequestViewModel.getContractDetailRequestList().getValue();
                long total = contractHandleViewModel.getTotal().getValue();
                long prepay = contractHandleViewModel.getPrepay().getValue();
                long discount = contractHandleViewModel.getDiscount().getValue();
                ContractRequest newContractRequest = new ContractRequest(customer.getCus_id(),employee.getAuth_id(),date_create,date_endAt,total,prepay,discount,contractRequests);
                contractViewModel.createContract(newContractRequest);
            }
        });

        contractDetailAdapter.setOnClickDeleteListener(position -> {
            if(contractDetailAdapter.getItem(position)!=null){
            confirmDeleteContractDress(contractDetailAdapter.getItem(position));
            }
        });

        layout_input_createAt.getEditText().setOnClickListener(view -> {
            showDialogChooseDate(view,layout_input_createAt);
            contractHandleViewModel.setDateCreateAt(layout_input_createAt.getEditText().getText().toString());
        });
        layout_input_endAt.getEditText().setOnClickListener(view -> {
            showDialogChooseDate(view,layout_input_endAt);
            contractHandleViewModel.setDateEndAt(layout_input_endAt.getEditText().getText().toString());
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
     * Hiển thị hộp thoại để xác nhận loại bỏ dress khỏi danh sách
     * @param contractDetail - thông tin dress trong contract
     */
    private void confirmDeleteContractDress(ContractDetail mcontractDetail){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle("Cảnh báo!")
                .setMessage("Bạn có chắc muốn bỏ thông tin này không?")
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    contractHandleViewModel.removeDress(mcontractDetail);
                    ContractDetailRequest contractDetaiRequest = new ContractDetailRequest(mcontractDetail.getDress().getId(),mcontractDetail.getRental_date(),mcontractDetail.getReturn_date());
                    contractRequestViewModel.removeDress(contractDetaiRequest);
                    contractHandleViewModel.updateTotalAmount();
                    dialog.dismiss();
                    Toast.makeText(requireContext(), "Loại bỏ thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.no, ((dialog, which) -> {
                    dialog.dismiss();
                }));
        builder.create().show();
    }
    /**
     * Kiểm tra dữ liệu các thành phần khi nhấn button "thêm mới"
     * - Chọn khách hàng, chọn ngày tạo hợp đồng, ngày kết thúc
     * - Danh sách các áo cưới được chọn
     *
     * @return boolean
     * - Trả về 'true' nếu tất cả các điều kiện xác nhận đều được thoả mãn
     * - ngược lại trả về 'false'.
     * @author NghiaTc
     */
    private boolean validateInput(){
        boolean isValid = true;
        if(contractHandleViewModel.getSelectCustomer().getValue() == null){
            layout_input_customer.setError("Vui lòng chọn khách hàng!");
            layout_input_customer.getEditText().requestFocus();
            isValid = false;
        }else{
            layout_input_customer.setError(null);
        }

        String createAt = layout_input_createAt.getEditText().getText().toString().trim();
        String endAt = layout_input_endAt.getEditText().getText().toString().trim();

        if(createAt.isEmpty()){
            layout_input_createAt.setError("Chọn lại!");
            layout_input_createAt.getEditText().requestFocus();
            isValid = false;
        }else if(endAt.isEmpty()){
            layout_input_endAt.setError("Chọn lại!");
            layout_input_endAt.getEditText().requestFocus();
            isValid = false;
        }else if(FormatHelper.convertStringtoDate(endAt).compareTo(FormatHelper.convertStringtoDate(createAt))<=0){
            layout_input_endAt.setError("Chọn lại!");
            layout_input_endAt.getEditText().requestFocus();
            isValid = false;
        }else{
            layout_input_createAt.setError(null);
            layout_input_endAt.setError(null);
        }

        if(contractHandleViewModel.getListContractDetail().getValue() == null){
            Toast.makeText(requireActivity(), "Vui lòng lựa chọn danh sách áo cưới!", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }
    /**
     * @desc kiểm tra lấy dữ liệu từ danh sách khách hàng
     * Lưu trữ dữ liệu vào 1 viewmodel mới
     */
    private void observeDataCustomer(){
        customerViewModel.getCustomerList().observe(getViewLifecycleOwner(),customers -> {
            if(customers!=null){
                contractHandleViewModel.setListCustomer(customers);
            }
        });

        contractHandleViewModel.getListCustomer().observe(getViewLifecycleOwner(), customers -> {
            if(customers!=null){
                setCustomerAdapter(customers);
            }
        });

        contractHandleViewModel.getSelectCustomer().observe(getViewLifecycleOwner(), customer -> {
            if(customer!=null){
                layout_input_customer_phone.getEditText().setText(FormatHelper.formatPhoneNumber(customer.getCus_phone()));
                layout_input_customer_name.getEditText().setText(customer.getCus_name());
            }
        });
    }
    /**
     * @desc kiểm tra lấy dữ liệu từ các thành phần hợp đồng.
     * Lấy dự liệu ra từ ContractHandleViewModel.
     * Tổng giá tiền.
     * Tiền giảm giá.
     * Tiền trả trước.
     * Ngày khỏi tạo hợp đồng
     * Ngày kết thúc hợp đồng
     */
    private void observeDataContract(){
        contractHandleViewModel.getDateCreateAt().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                layout_input_createAt.getEditText().setText(s);
            }
        });
        contractHandleViewModel.getDateEndAt().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                layout_input_endAt.getEditText().setText(s);
            }
        });
        contractHandleViewModel.getDiscount().observe(getViewLifecycleOwner(), aLong -> {
            if (aLong != null) {
                String currentValue = layout_input_discount.getEditText().getText().toString();
                String newValue = String.valueOf(aLong);
                if (!currentValue.equals(newValue)) {
                    layout_input_discount.getEditText().setText(newValue);
                }
            }
        });

        contractHandleViewModel.getPrepay().observe(getViewLifecycleOwner(), aLong -> {
            if (aLong != null) {
                String currentValue = layout_input_prepay.getEditText().getText().toString();
                String newValue = String.valueOf(aLong);
                if (!currentValue.equals(newValue)) {
                    layout_input_prepay.getEditText().setText(newValue);
                }
            }
        });
        contractHandleViewModel.getTotal().observe(getViewLifecycleOwner(),aLong -> {
            if(aLong!=null){
                layout_input_total.getEditText().setText(FormatHelper.convertPriceToString(aLong));
            }
        });
        contractHandleViewModel.getListContractDetail().observe(getViewLifecycleOwner(),contractDetails -> {
            if (contractDetails!=null){
                listContractDetail.clear();
                listContractDetail.addAll(contractDetails);
                contractDetailAdapter.setList(listContractDetail);
                contractHandleViewModel.updateTotalAmount();
            }
        });

    }
    /**
    * @desc mapping layout
    */
    private void mapping(View view){
        layout_input_customer = view.findViewById(R.id.layout_input_customer);
        dropdown_customer = view.findViewById(R.id.dropdown_customer);
        layout_input_customer_phone = view.findViewById(R.id.layout_input_customer_phone);
        layout_input_customer_name = view.findViewById(R.id.layout_input_customer_name);
        layout_input_createAt = view.findViewById(R.id.layout_input_createAt);
        layout_input_endAt = view.findViewById(R.id.layout_input_endAt);
        tv_employee_name = view.findViewById(R.id.tv_employee_name);
        tv_choose_dress = view.findViewById(R.id.tv_choose_dress);
        recyclerView = view.findViewById(R.id.recyclerView);
        layout_input_total = view.findViewById(R.id.layout_input_total);
        layout_input_discount = view.findViewById(R.id.layout_input_discount);
        layout_input_prepay = view.findViewById(R.id.layout_input_prepay);
        btn_create_item = view.findViewById(R.id.btn_create_item);

        listContractDetail = new ArrayList<>();
        contractDetailAdapter = new ContractDetailAdapter(requireActivity(),listContractDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(contractDetailAdapter);
    }

    /**
     * @param customerList
     * Thực hiện việc load lại danh sách customer
     */
    private void setCustomerAdapter(List<Customer> customerList){
        customerArrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line,customerList);
        dropdown_customer.setAdapter(customerArrayAdapter);
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
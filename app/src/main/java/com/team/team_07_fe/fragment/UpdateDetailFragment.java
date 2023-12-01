package com.team.team_07_fe.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Customer;
import com.team.team_07_fe.models.DetailStatistics;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.request.CustomerRequest;
import com.team.team_07_fe.request.DetailStatisticsRequest;
import com.team.team_07_fe.ui.dress.DressUpdateFragment;
import com.team.team_07_fe.utils.FormatHelper;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.DetailStatisticsViewModel;
import com.team.team_07_fe.viewmodels.DressViewModel;

import java.util.Date;


public class UpdateDetailFragment extends Fragment {


    private TextInputLayout  update_detail_input_date,update_detail_input_name,
            update_detail_input_money, update_detail_input_text;
    private AppCompatButton btn_reload_item, btn_update_item;
    private DetailStatisticsViewModel mDetailStatisticsViewModel;
    private LoadingDialog loadingDialog;
    private DetailStatistics originalData = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_detail, container, false);
        mDetailStatisticsViewModel = new ViewModelProvider(requireActivity()).get(DetailStatisticsViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        mapping (view);
        return (view);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeData();

        if(getArguments()!=null){
            originalData = (DetailStatistics) getArguments().getSerializable("data_detail");
            setData(originalData);
        }
        //Click button
        btn_reload_item.setOnClickListener(this::handleReloadData);
        btn_update_item.setOnClickListener(this::updateDetail);
    }
    private void observeData(){
        mDetailStatisticsViewModel.getDataInput().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                refreshFragment();
                mDetailStatisticsViewModel.setDataInput(null);
            }
        });
        mDetailStatisticsViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s->{
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                mDetailStatisticsViewModel.setDataInput(null);
            }
        });
    }
    private void handleReloadData(View view){
        if(originalData!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle("Thông báo!")
                    .setMessage("Bạn có chắc chắn muốn hoàn tác tất cả thay đổi này không? " +
                            "Mọi thay đổi sẽ không được lưu.")
                    .setPositiveButton(R.string.yes,(dialog, which) -> {
                        setData(originalData);
                        dialog.dismiss();
                    })
                    .setNegativeButton(R.string.no,((dialog, which) -> {
                        dialog.dismiss();
                    }));
            builder.create().show();
        }
    }
    private void updateDetail(View view){
        String name = update_detail_input_name.getEditText().getText().toString().trim();
        String money = update_detail_input_money.getEditText().getText().toString().trim();
        String text = update_detail_input_text.getEditText().getText().toString().trim();
        DetailStatisticsRequest detailStatisticsRequest = new DetailStatisticsRequest(name, Long.parseLong(money),text);
        String id = null;
        showDialogConfirmUpdate(id,detailStatisticsRequest);

        if(valueInput(name,money,text)){
            Date formatBirthday = null;
            if (!TextUtils.isEmpty(null)) {
                formatBirthday = FormatHelper.convertStringtoDate(null);


                Date currentDate = new Date();
                if (formatBirthday != null && formatBirthday.before(currentDate)) {


                    return; // Dừng việc tạo yêu cầu
                }
            }
            DetailStatisticsRequest detailStatisticsRequest1 = new DetailStatisticsRequest(name,Long.parseLong(money),text);
            showDialogConfirmUpdate(id,detailStatisticsRequest1);
        }
    }

    private void showDialogConfirmUpdate(String id, DetailStatisticsRequest detailStatisticRequest){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle("Thông báo!")
                .setMessage("Bạn có chắc muốn cập nhật mục này không " +
                        "Hành động này sẽ không thể hoàn tác.")
                .setPositiveButton(R.string.yes,(dialog, which) -> {
                    mDetailStatisticsViewModel.updateDetailStatistics(id, detailStatisticRequest);
                    refreshFragment();
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.no,((dialog, which) -> {
                    dialog.dismiss();
                }));
        builder.create().show();
    }
//    public static void showAlertDialog(Context context, String title, String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//        builder.setTitle(title)
//                .setMessage(message)
//                .setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // Xử lý khi người dùng bấm OK
//                        dialog.dismiss(); // Đóng dialog
//                    }
//                });
//        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                // xu li khi nguoi dung bam HUY
//                dialogInterface.dismiss();
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
    private void setData(DetailStatistics detailStatistics){
        // Set lại tên khoản chi
        if (update_detail_input_name.getEditText() != null) {
            update_detail_input_name.getEditText().setText(String.valueOf(detailStatistics.getDt_name()));
        }
        // Set lại số tiền
        update_detail_input_money.getEditText().setText(Math.toIntExact(detailStatistics.getDt_money()));

        // Set lại mô tả
        update_detail_input_text.getEditText().setText(detailStatistics.getDt_text());



    }

    public boolean valueInput(String name,
                              String money, String text){
        boolean isvalid = true;
        if (TextUtils.isEmpty(name)) {
            update_detail_input_name.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            update_detail_input_name.setError(null);
        }
        if (TextUtils.isEmpty(money)) {
            update_detail_input_money.setError("Không để trống dữ liệu!");
            isvalid = false;
        } else {
            update_detail_input_money.setError(null);
        }
        if (TextUtils.isEmpty(text)) {
            update_detail_input_text.setError(null);
            isvalid = false;
        } else {
            update_detail_input_text.setError(null);
        }

        return isvalid;
    }
    public void mapping(View view){
        update_detail_input_date = view.findViewById(R.id.update_detail_input_date);
        update_detail_input_name = view.findViewById(R.id.update_detail_input_name);
        update_detail_input_money = view.findViewById(R.id.update_detail_input_money);
        update_detail_input_text = view.findViewById(R.id.update_detail_input_text);
        btn_reload_item = view.findViewById(R.id.btn_reload_item);
        btn_update_item = view.findViewById(R.id.btn_update_item);
    }
    private void refreshFragment(){
        loadingDialog.dismiss();
        NavHostFragment.findNavController(UpdateDetailFragment.this)
                .popBackStack();
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
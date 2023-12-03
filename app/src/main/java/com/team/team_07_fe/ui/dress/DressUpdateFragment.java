package com.team.team_07_fe.ui.dress;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;

import com.team.team_07_fe.models.Dress;

import com.team.team_07_fe.request.DressRequest;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.DressViewModel;


public class DressUpdateFragment extends Fragment {
    private TextInputLayout layout_input_id, layout_input_name, layout_input_type, layout_input_color,
            layout_input_size, layout_input_price, layout_input_des;
    private DressViewModel mViewModel;
    private AppCompatButton btn_reloadDress, btn_updateDress;
    private ImageView imageView;
    private Uri mUri;
    private Dress originalData = null;
    private LoadingDialog loadingDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        observeData();
        View view = inflater.inflate(R.layout.fragment_update_dress, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(DressViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        mapping (view);
        return (view);
    }

    private void observeData() {
        mViewModel.getDataInput().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                refreshFragment();
                mViewModel.setDataInput(null);
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s->{
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                mViewModel.setDataInput(null);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            originalData = (Dress) getArguments().getSerializable("data_dress");
            setData(originalData);
        }
        //Click button
        imageView.setOnClickListener(view1 -> choseImgFromGallery());
        btn_reloadDress.setOnClickListener(this::handleReloadData);
        btn_updateDress.setOnClickListener(this::handleUpdateData);
    }
    private void handleReloadData(View view){
        if(originalData!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle("Cảnh báo!")
                    .setMessage("Bạn có chắc với quyết định hoàn tác này không? " +
                            "Mọi thay đổi của bạn sẽ không được lưu.")
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
    private void handleUpdateData(View view) {
        String id = layout_input_id.getEditText().getText().toString().trim();
        String name = layout_input_name.getEditText().getText().toString().trim();
        String type = layout_input_type.getEditText().getText().toString().trim();
        String price = layout_input_price.getEditText().getText().toString().trim();
        String size = layout_input_size.getEditText().getText().toString().trim();
        String color = layout_input_color.getEditText().getText().toString().trim();
        String des = layout_input_des.getEditText().getText().toString().trim();



        if (validateInput(name, id, price)) {

            DressRequest dressRequest = new DressRequest( id, name, type,  size, color, Long.parseLong(price),des);
            showDialogConfirmUpdate(id, dressRequest);
        }

    }
        private void showDialogConfirmUpdate(String id, DressRequest dressRequest){
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle("Thông báo!")
                    .setMessage("Bạn có chắc muốn cập nhật áo cưới này không? " +
                            "Mọi thông tin trước đó sẽ không được lưu.")
                    .setPositiveButton(R.string.yes,(dialog, which) -> {
                        //Cần xử lý lại khi gửi qua server
                        loadingDialog.show();
                        mViewModel.updateDress(id,dressRequest);
                        dialog.dismiss();
                    })
                    .setNegativeButton(R.string.no,((dialog, which) -> {
                        dialog.dismiss();
                    }));
            builder.create().show();
        }


    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý khi người dùng bấm OK
                        dialog.dismiss(); // Đóng dialog
                    }
                });
        builder.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // xu li khi nguoi dung bam HUY
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private boolean validateInput(String name,String id, String price){
        boolean isValid = true;

        if(TextUtils.isEmpty(name)){
            layout_input_name.setError("Vui lòng nhập tên áo cưới!");
            isValid = false;
        }else{
            layout_input_name.setError(null);
        }

        if(TextUtils.isEmpty(id) ){
            layout_input_id.setError("Vui lòng nhập đúng định dạng!");
            isValid = false;
        }else{
            layout_input_id.setError(null);
        }

        if(TextUtils.isEmpty(price)){
            layout_input_price.setError("Vui lòng nhập giá áo cưới!");
            isValid = false;
        }else{
            layout_input_price.setError(null);
        }


        return isValid;
    }
    private void mapping(View view){
        imageView = view.findViewById(R.id.imageView);
        layout_input_id = view.findViewById(R.id.layout_input_id);
        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_price = view.findViewById(R.id.layout_input_price);
        layout_input_type = view.findViewById(R.id.layout_input_type);
        layout_input_color = view.findViewById(R.id.layout_input_color);
        layout_input_size = view.findViewById(R.id.layout_input_size);
        layout_input_des = view.findViewById(R.id.layout_input_des);
        btn_reloadDress = view.findViewById(R.id.btn_reloadDress);
        btn_updateDress = view.findViewById(R.id.btn_updateDress);
    }

    private void setData(Dress dress) {
        // Set lại thông tin id, nếu có trường hiển thị id
        if (layout_input_id.getEditText() != null) {
            layout_input_id.getEditText().setText(dress.getId());
        }
        // Set lại tên
        layout_input_name.getEditText().setText(dress.getDress_name());
        // Set lại mau
        layout_input_color.getEditText().setText(dress.getColor());
        // Set lại gia ao
        layout_input_price.getEditText().setText(String.valueOf(dress.getDress_price()));
        // Set lại mo ta
        layout_input_des.getEditText().setText(dress.getDress_description());
    }
    private void refreshFragment(){
        loadingDialog.dismiss();
        NavHostFragment.findNavController(DressUpdateFragment.this)
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
    public void choseImgFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Đã chọn ảnh"));
    }

    // nhận uri khi chọn ảnh từ thư viện
    private final ActivityResultLauncher<Intent> mActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            if (data == null){
                                return;
                            }
                            mUri = data.getData();
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mUri);
                                imageView.setImageBitmap(bitmap);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
}
package com.team.team_07_fe.ui.dress;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.R;
import com.team.team_07_fe.models.Dress;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.DressViewModel;


public class DressAddFragment extends Fragment {
    private TextInputLayout layout_input_name, layout_input_type, layout_input_color, layout_input_size,
    layout_input_price, layout_input_des;
    private DressViewModel mViewModel;
    private AppCompatButton btn_add_item;
    private ImageView imageView;
    private LoadingDialog loadingDialog;
    private Uri mUri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dress_add, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(DressViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        mapping(view);
        //mapping
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void handle(View view){
        String name = layout_input_name.getEditText().getText().toString().trim();
        String type = layout_input_type.getEditText().getText().toString().trim();
        String price = layout_input_price.getEditText().getText().toString().trim();
        String color = layout_input_color.getEditText().getText().toString().trim();
        String size = layout_input_size.getEditText().getText().toString().trim();
        String des = layout_input_des.getEditText().getText().toString().trim();


        if(validateInput(name,price,type, des)){
         //   mViewModel.addDress(new Dress(name,type,price,color,size,des));///đang tìm lỗi
            Toast.makeText(requireContext(), "Thêm mới áo cưới thành công!", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        }
    }
    private void mapping(View view){
        imageView = view.findViewById(R.id.imageView);
        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_price = view.findViewById(R.id.layout_input_price);
        layout_input_type = view.findViewById(R.id.layout_input_type);
        layout_input_color = view.findViewById(R.id.layout_input_color);
        layout_input_size = view.findViewById(R.id.layout_input_size);
        layout_input_des = view.findViewById(R.id.layout_input_des);
        btn_add_item = view.findViewById(R.id.btn_add_item);

    }
    private boolean validateInput(String name,String price,
                                  String color, String des){
        boolean isValid = true;

        if(TextUtils.isEmpty(name)){
            layout_input_name.setError("Vui lòng nhập tên áo cưới!");
            isValid = false;
        }else{
            layout_input_name.setError(null);
        }

        if(TextUtils.isEmpty(price) ){
            layout_input_price.setError("Vui lòng nhập giá tiền!");
            isValid = false;
        }else{
            layout_input_price.setError(null);
        }
        if (TextUtils.isEmpty(color)) {
            layout_input_color.setError("Vui lòng nhập màu cho áo cưới!");
            isValid = false;
        }else{
            layout_input_color.setError(null);
        }
        if(TextUtils.isEmpty(des)){
            layout_input_des.setError(null);
        }else  {
            layout_input_des.setError("Vui lòng nhập mô tả cho áo cưới!");
            isValid = false;
        }

        return isValid;
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
    private void callApiResgister() {

    }

}
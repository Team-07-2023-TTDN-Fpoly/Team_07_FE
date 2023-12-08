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
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.team.team_07_fe.MainActivity;
import com.team.team_07_fe.R;

import com.team.team_07_fe.models.Dress;

import com.team.team_07_fe.models.DressType;
import com.team.team_07_fe.request.DressRequest;
import com.team.team_07_fe.ui.dresstype.DressTypeViewModel;
import com.team.team_07_fe.utils.LoadingDialog;
import com.team.team_07_fe.viewmodels.DressViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class DressUpdateFragment extends Fragment {
    private static final int SELECT_PICTURE = 1;

    private TextInputLayout layout_input_id, layout_input_name, layout_input_type, layout_input_color,
            layout_input_size, layout_input_price, layout_input_des;
    private AutoCompleteTextView dropdown_type_dress,dropdown_size,dropdown_status;

    private DressViewModel mViewModel;
    private DressTypeViewModel dressTypeViewModel;
    private AppCompatButton btn_reloadDress, btn_updateDress;
    private ImageView imageView;
    private Uri mUri =null;
    private Dress originalData = null;
    private LoadingDialog loadingDialog;

    //Adapter danh sách loại áo cưới
    private ArrayAdapter<DressType> dressTypeArrayAdapter;
    private List<DressType> listDressTypeForDropdown;
    //Lựa chọn loại áo cưới
    private DressType selectDressTypeFromDropdown = null;
    //Adapter cho size áo
    private ArrayAdapter<String> sizeArrayAdapter;
    private final String[] stringsSize = {"S","M","L"};
    private final List<String> listSizeForDropdown = Arrays.asList(stringsSize);
    //Lựa chọn size áo
    private String selectSizeFromDropdown ="";

    //Adapter cho tình trạng
    private final String[] stringsStatus ={"Sẵn sàng","Đang giặt","Cho thuê"};
    private ArrayAdapter<String> statusArrayAdapter;
    private final List<String> lisStatusForDropdown = Arrays.asList(stringsStatus);
    //Lựa chọn tình trạng
    private String selectStatusFromDropdown = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_dress, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(DressViewModel.class);
        dressTypeViewModel = new ViewModelProvider(requireActivity()).get(DressTypeViewModel.class);
        loadingDialog = new LoadingDialog(requireContext());
        mapping (view);
        return (view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dressTypeViewModel.getAllDressType(null);
        if(getArguments()!=null){
            Dress data = (Dress) getArguments().getSerializable("data_dress");
            originalData = data;
            setData(data);
        }


        dressTypeViewModel.getDressTypeList().observe(getViewLifecycleOwner(),dressTypes -> {
            if(dressTypes!=null){
                //Dropdown list DressType
                dressTypeArrayAdapter = new ArrayAdapter<>(requireContext(),android.R.layout.simple_dropdown_item_1line,dressTypes);
                dropdown_type_dress.setAdapter(dressTypeArrayAdapter);
            }
        });


        handleAdapter();

        //Click button
        imageView.setOnClickListener(view1 -> choseImgFromGallery());
        btn_reloadDress.setOnClickListener(this::handleReloadData);
        btn_updateDress.setOnClickListener(this::handleUpdateData);

        observeData();
    }

    /**
     * Xử lý đổ dữ liệu vào adapter và lựa chọn
     */
    private void handleAdapter() {

        //Dropdown list Size
        sizeArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line,listSizeForDropdown);
        dropdown_size.setAdapter(sizeArrayAdapter);
        //Dropdown list status
        statusArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line,lisStatusForDropdown);
        dropdown_status.setAdapter(statusArrayAdapter);
        //Lựa chọn dressType
        dropdown_type_dress.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectDressTypeFromDropdown = (DressType) dressTypeArrayAdapter.getItem(i);
        });
        //Lựa chọn size
        dropdown_size.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectSizeFromDropdown = sizeArrayAdapter.getItem(i);
        });
        //Lựa chọn status
        dropdown_status.setOnItemClickListener((adapterView, view1, i, l) -> {
            selectStatusFromDropdown = statusArrayAdapter.getItem(i);
        });
    }

    private void observeData() {
        mViewModel.getDataMessage().observe(getViewLifecycleOwner(),s -> {
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                refreshFragment();
                mViewModel.setDataMessage(null);
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(),s->{
            if(s!=null){
                loadingDialog.dismiss();
                Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                mViewModel.setErrorMessage(null);
            }
        });
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
        String price  = layout_input_price.getEditText().getText().toString().trim();
        String color = layout_input_color.getEditText().getText().toString().trim();
        String size = selectSizeFromDropdown;
        String des = layout_input_des.getEditText().getText().toString().trim();
        String status = selectStatusFromDropdown;


        if (validateInput(name, price)) {
            loadingDialog.show();
            if(mUri == null){
                showConfirmUpdate(id,null,null,name,selectDressTypeFromDropdown.getType_id(),color,size,Long.parseLong(price),des,status);
            }
            try {
                // Truy xuất dữ liệu từ URI
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(mUri);

                // Lưu dữ liệu vào một tệp mới trong thư mục tạm thời của ứng dụng
                File tempFile = createTempFile();
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                //Tạo 1 request body để truyền ảnh
                RequestBody requestFile = RequestBody.create(MediaType.parse(requireActivity().getContentResolver().getType(mUri)), tempFile);

                showConfirmUpdate(id,requestFile,tempFile,name,selectDressTypeFromDropdown.getType_id(),color,size,Long.parseLong(price),des,status);

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    private void showConfirmUpdate(String id,RequestBody mUri, File tempFile, String name, String type, String color, String size, long price, String des,String status){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle("Thông báo!")
                .setMessage("Bạn có chắc muốn cập nhật áo cưới này không? " +
                        "Mọi thông tin trước đó sẽ không được lưu.")
                .setPositiveButton(R.string.yes,(dialog, which) -> {
                    //Cần xử lý lại khi gửi qua server
                    mViewModel.updateDress(id,mUri,tempFile,name,type,color,size,price,des,status);
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
    private boolean validateInput(String name, String price){
        boolean isValid = true;

        if(TextUtils.isEmpty(name)){
            layout_input_name.setError("Vui lòng nhập tên áo cưới!");
            isValid = false;
        }else{
            layout_input_name.setError(null);
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
        imageView = view.findViewById(R.id.layout_image);
        layout_input_id = view.findViewById(R.id.layout_input_id);
        layout_input_name = view.findViewById(R.id.layout_input_name);
        layout_input_price = view.findViewById(R.id.layout_input_price);
        layout_input_type = view.findViewById(R.id.layout_input_type);
        layout_input_color = view.findViewById(R.id.layout_input_color);
        layout_input_size = view.findViewById(R.id.layout_input_size);
        layout_input_des = view.findViewById(R.id.layout_input_des);
        btn_reloadDress = view.findViewById(R.id.btn_reloadDress);
        btn_updateDress = view.findViewById(R.id.btn_updateDress);
        dropdown_type_dress = view.findViewById(R.id.dropdown_type_dress);
        dropdown_size = view.findViewById(R.id.dropdown_size);
        dropdown_status = view.findViewById(R.id.dropdown_status);


    }

    private void setData(Dress dress) {
        if(dress.getImage()!=null){
            Glide.with(requireActivity()).load(dress.getImage()).into(imageView);
        }
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

        if(dress.getDressTypeId()!=null){
            dropdown_type_dress.setText(dress.getDressTypeId().toString(),false);
            selectDressTypeFromDropdown = dress.getDressTypeId();
        }
        if(dress.getSize()!=null){
            dropdown_size.setText(dress.getSize(),false);
            selectSizeFromDropdown = dress.getSize();
        }
        if(dress.getDress_status()!=null){
            dropdown_status.setText(dress.getDress_status(),false);
            selectStatusFromDropdown = dress.getDress_status();
        }
    }

    private void refreshFragment(){
        loadingDialog.dismiss();
        NavHostFragment.findNavController(DressUpdateFragment.this)
                .popBackStack();
    }




    public void choseImgFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
//        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Đã chọn ảnh"),SELECT_PICTURE);
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

    //
    private File createTempFile() throws IOException {
        // Tạo tên tệp mới dựa trên thời gian hiện tại
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp;

        // Lấy đường dẫn tới thư mục tạm thời của ứng dụng
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Tạo tệp mới
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                if (data != null) {
                    // Get the url of the image from data
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        // update the preview image in the layout
                        imageView.setImageURI(selectedImageUri);
                        //
                        mUri = selectedImageUri;
                    }
                }
            }
        }
    }
}
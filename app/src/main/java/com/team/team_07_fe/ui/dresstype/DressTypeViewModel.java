package com.team.team_07_fe.ui.dresstype;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team.team_07_fe.models.DressType;

import java.util.ArrayList;
import java.util.List;

public class DressTypeViewModel extends ViewModel {
    private MutableLiveData<List<DressType>> listdr;
    private List<DressType> originalData;
    private int typeCounter = 5;
    public DressTypeViewModel(){
        listdr = new MutableLiveData<>();
        initializeExampleList();
        originalData = new ArrayList<>(listdr.getValue());
    }

    public LiveData<List<DressType>> getEmployeeList() {
        return listdr;
    }

    public void addDressType(String type_name) {
        int newTypeId = typeCounter++;
        DressType dressType = new DressType(newTypeId, type_name);
        List<DressType> currentList = listdr.getValue();
        if (currentList != null) {
            currentList.add(dressType);
            listdr.setValue(new ArrayList<>(currentList));
            originalData = new ArrayList<>(currentList);
        }
    }
    public void updateDressType(int type_id, String type_name) {
        List<DressType> currentList = listdr.getValue();

        if (currentList != null) {
            for (DressType dressType : currentList) {
                if (dressType.getType_id() == type_id) {
                    dressType.setType_id(type_id);
                    dressType.setType_name(type_name);
                    break;
                }
            }

            listdr.setValue(new ArrayList<>(currentList));
            originalData = new ArrayList<>(currentList);
        }
    }
    public void deleteDressType(int type_id) {
        List<DressType> currentList = listdr.getValue();
        if (currentList != null) {
            currentList.removeIf(dressType -> dressType.getType_id() == type_id);
            listdr.setValue(new ArrayList<>(currentList));
            originalData = new ArrayList<>(currentList);
        }
    }

    public List<DressType> getOriginalData() {
        return originalData;
    }



    private void initializeExampleList() {
        List<DressType> dressTypes = new ArrayList<>();

        dressTypes.add(new DressType(1,"áo dài"));
        dressTypes.add(new DressType(2,"vest"));
        dressTypes.add(new DressType(3,"sườn xám"));
        dressTypes.add(new DressType(4, "váy đuôi cá"));


        listdr.setValue(dressTypes);
        originalData = new ArrayList<>(dressTypes);
    }
}

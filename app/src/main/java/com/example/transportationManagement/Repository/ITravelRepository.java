package com.example.transportationManagement.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.transportationManagement.Model.ITravelDataSource;
import com.example.transportationManagement.Entities.Travel;

import java.util.List;

public interface ITravelRepository {

    void addTravel(Travel travel);
    void updateTravel(Travel travel);
    List<Travel> getAllTravels();
    MutableLiveData<Boolean> getIsSuccess();
    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l);
}

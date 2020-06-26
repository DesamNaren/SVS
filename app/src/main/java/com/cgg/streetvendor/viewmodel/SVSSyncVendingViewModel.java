package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.room.repository.SVSSyncVendingRepository;
import com.cgg.streetvendor.source.reposnse.kyc.BusinessEntity;
import com.cgg.streetvendor.source.reposnse.kyc.BusinessResponse;
import com.cgg.streetvendor.source.reposnse.kyc.CasteEntity;
import com.cgg.streetvendor.source.reposnse.kyc.CasteResponse;
import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.cgg.streetvendor.source.reposnse.kyc.GenderResponse;
import com.cgg.streetvendor.source.reposnse.kyc.PWDEntity;
import com.cgg.streetvendor.source.reposnse.kyc.PWDResponse;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationResponse;
import com.cgg.streetvendor.source.reposnse.kyc.RelationResponse;
import com.cgg.streetvendor.source.reposnse.kyc.VendingAddressEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingAddressResponse;
import com.cgg.streetvendor.source.reposnse.kyc.VendingTypeEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingTypeResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SVSSyncVendingViewModel extends AndroidViewModel {
    private MutableLiveData<BusinessResponse> businessResponseMutableLiveData;
    private MutableLiveData<VendingTypeResponse> vendingTypeResponseMutableLiveData;
    private MutableLiveData<VendingAddressResponse> vendingAddressResponseMutableLiveData;
    private LiveData<List<BusinessEntity>> allBusiness;
    private LiveData<List<VendingTypeEntity>> allVenType;
    private LiveData<List<VendingAddressEntity>> allVenAdd;
    private SVSSyncVendingRepository svsSyncVendingRepository;

    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;

    public SVSSyncVendingViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        svsSyncVendingRepository = new SVSSyncVendingRepository(application);
        businessResponseMutableLiveData = new MutableLiveData<>();
        vendingTypeResponseMutableLiveData = new MutableLiveData<>();
        vendingAddressResponseMutableLiveData = new MutableLiveData<>();
        allBusiness = new MutableLiveData<>();
        allVenType = new MutableLiveData<>();
        allVenAdd = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }


    public LiveData<String> getBusinessId(String business) {
        return svsSyncVendingRepository.getBusinessId(business);
    }

    public LiveData<String> getTelBusinessId(String business) {
        return svsSyncVendingRepository.getTelBusinessId(business);
    }


    public LiveData<String> getVenTypeId(String vType) {
        return svsSyncVendingRepository.getVenTypeId(vType);
    }

    public LiveData<String> getTelVenTypeId(String vType) {
        return svsSyncVendingRepository.getTelVenTypeId(vType);
    }



    public LiveData<String> getVenAddressId(String vAddress, String ULBId,String distId) {
        return svsSyncVendingRepository.getVenAddressId(vAddress, ULBId, distId);
    }


    public LiveData<String> getTelVenAddressId(String vAddress, String ULBId,String distId) {
        return svsSyncVendingRepository.getTelVenAddressId(vAddress, ULBId, distId);
    }


    public LiveData<List<BusinessEntity>> getAllBusiness() {
        if (allBusiness != null) {
            allBusiness = svsSyncVendingRepository.getAllBusiness();
        }
        return allBusiness;
    }

    public LiveData<List<VendingTypeEntity>> getAllVenTypes() {
        if (allVenType != null) {
            allVenType = svsSyncVendingRepository.getAllVenTypes();
        }
        return allVenType;
    }

    public LiveData<List<VendingAddressEntity>> getAllVenAddress() {
        if (allVenAdd != null) {
            allVenAdd = svsSyncVendingRepository.getAllVenAddress();
        }
        return allVenAdd;
    }


    public LiveData<List<VendingAddressEntity>> getAllVenAddress(String ulbId, String distId) {
        if (allVenAdd != null) {
            allVenAdd = svsSyncVendingRepository.getAllVenAddress(ulbId, distId);
        }
        return allVenAdd;
    }

    public LiveData<BusinessResponse> getBusinessResponse() {
        if (businessResponseMutableLiveData != null) {
            getBusinessResponseCall();
        }
        return businessResponseMutableLiveData;
    }

    private void getBusinessResponseCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getBusinessResponse().enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(@NotNull Call<BusinessResponse> call, @NotNull Response<BusinessResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    businessResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<BusinessResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<VendingTypeResponse> getVenTypeResponse() {
        if (vendingTypeResponseMutableLiveData != null) {
            getVenTypeMasterCall();
        }
        return vendingTypeResponseMutableLiveData;
    }

    private void getVenTypeMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getVenTypeResponse().enqueue(new Callback<VendingTypeResponse>() {
            @Override
            public void onResponse(@NotNull Call<VendingTypeResponse> call, @NotNull Response<VendingTypeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    vendingTypeResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<VendingTypeResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<VendingAddressResponse> getVenAddressResponse() {
        if (vendingAddressResponseMutableLiveData != null) {
            getVenAddressMasterCall();
        }
        return vendingAddressResponseMutableLiveData;
    }

    private void getVenAddressMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getVenAddressResponse().enqueue(new Callback<VendingAddressResponse>() {
            @Override
            public void onResponse(@NotNull Call<VendingAddressResponse> call, @NotNull Response<VendingAddressResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    vendingAddressResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<VendingAddressResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}




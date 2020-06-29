package com.cgg.streetvendor.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.network.SVSService;
import com.cgg.streetvendor.room.repository.SVSSyncBBRepository;
import com.cgg.streetvendor.room.repository.SVSSyncPlacesRepository;
import com.cgg.streetvendor.source.reposnse.bankbranch.BankEntity;
import com.cgg.streetvendor.source.reposnse.bankbranch.BankResponse;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchEntity;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchResponse;
import com.cgg.streetvendor.source.reposnse.places.DistrictEntity;
import com.cgg.streetvendor.source.reposnse.places.DistrictResponse;
import com.cgg.streetvendor.source.reposnse.places.MandalEntity;
import com.cgg.streetvendor.source.reposnse.places.MandalResponse;
import com.cgg.streetvendor.source.reposnse.places.StateEntity;
import com.cgg.streetvendor.source.reposnse.places.StateResponse;
import com.cgg.streetvendor.source.reposnse.places.VillageEntity;
import com.cgg.streetvendor.source.reposnse.places.VillageResponse;
import com.cgg.streetvendor.source.reposnse.ulb.WardEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SVSSyncBBViewModel extends AndroidViewModel {
    private MutableLiveData<BankResponse> bankEntityMutableLiveData;
    private MutableLiveData<BranchResponse> branchResponseMutableLiveData;
    private Context context;
    private LiveData<List<BankEntity>> allBanks;
    private LiveData<List<BranchEntity>> allBranches;
    private LiveData<List<BranchEntity>> bankBranches;

    private ErrorHandlerInterface errorHandlerInterface;
    private SVSSyncBBRepository svsSyncBBRepository;
    public SVSSyncBBViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        svsSyncBBRepository = new SVSSyncBBRepository(application);
        bankEntityMutableLiveData = new MutableLiveData<>();
        branchResponseMutableLiveData = new MutableLiveData<>();
        allBanks = new MutableLiveData<>();
        allBranches = new MutableLiveData<>();
        bankBranches = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<String> getIFSCCode(String bankId, String branchId) {
        return svsSyncBBRepository.getIFSCCode(bankId, branchId);
    }

    public LiveData<String> getBankID(String bankName, int pos) {
        return svsSyncBBRepository.getBankID(bankName, pos);
    }

    public LiveData<String> getTelBankId(String bankName, int pos) {
        return svsSyncBBRepository.getTelBankId(bankName, pos);
    }


    public LiveData<String> getBranchID(String branchName, String bankId) {
        return svsSyncBBRepository.getBranchId(branchName, bankId);
    }

    public LiveData<String> getTelBranchId(String branchName, String bankId) {
        return svsSyncBBRepository.getTelBranchId(branchName, bankId);
    }

    public LiveData<List<BranchEntity>> getBankBranches(String bankId) {
        if (bankBranches != null) {
            bankBranches = svsSyncBBRepository.getBankBranches(bankId);
        }
        return bankBranches;
    }

    public LiveData<List<BankEntity>> getAllBanks() {
        if (allBanks != null) {
            allBanks = svsSyncBBRepository.getAllBanks();
        }
        return allBanks;
    }

    public LiveData<List<BranchEntity>> getAllBranches() {
        if (allBranches != null) {
            allBranches = svsSyncBBRepository.getAllBranches();
        }
        return allBranches;
    }

    public LiveData<BankResponse> getBankResponse() {
        if (bankEntityMutableLiveData != null) {
            getBankResponseCall();
        }
        return bankEntityMutableLiveData;
    }

    private void getBankResponseCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getBankResponse().enqueue(new Callback<BankResponse>() {
            @Override
            public void onResponse(@NotNull Call<BankResponse> call, @NotNull Response<BankResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bankEntityMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<BankResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<BranchResponse> getBranchResponse() {
        if (branchResponseMutableLiveData != null) {
            getBranchMasterCall();
        }
        return branchResponseMutableLiveData;
    }

    private void getBranchMasterCall() {
        SVSService twdService = SVSService.Factory.create();
        twdService.getBranchResponse().enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(@NotNull Call<BranchResponse> call, @NotNull Response<BranchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    branchResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<BranchResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}




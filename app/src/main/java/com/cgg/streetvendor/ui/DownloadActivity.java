package com.cgg.streetvendor.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cgg.streetvendor.R;
import com.cgg.streetvendor.application.AppConstants;
import com.cgg.streetvendor.application.SVSApplication;
import com.cgg.streetvendor.databinding.ActivityDownloadBinding;
import com.cgg.streetvendor.interfaces.ErrorHandlerInterface;
import com.cgg.streetvendor.interfaces.GCCDivisionInterface;
import com.cgg.streetvendor.room.repository.SVSSyncBBRepository;
import com.cgg.streetvendor.room.repository.SVSSyncKYCRepository;
import com.cgg.streetvendor.room.repository.SVSSyncPlacesRepository;
import com.cgg.streetvendor.room.repository.SVSSyncULBRepository;
import com.cgg.streetvendor.room.repository.SVSSyncVendingRepository;
import com.cgg.streetvendor.source.reposnse.bankbranch.BankEntity;
import com.cgg.streetvendor.source.reposnse.bankbranch.BankResponse;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchEntity;
import com.cgg.streetvendor.source.reposnse.bankbranch.BranchResponse;
import com.cgg.streetvendor.source.reposnse.kyc.BusinessEntity;
import com.cgg.streetvendor.source.reposnse.kyc.BusinessResponse;
import com.cgg.streetvendor.source.reposnse.kyc.CasteEntity;
import com.cgg.streetvendor.source.reposnse.kyc.CasteResponse;
import com.cgg.streetvendor.source.reposnse.kyc.GenderEntity;
import com.cgg.streetvendor.source.reposnse.kyc.GenderResponse;
import com.cgg.streetvendor.source.reposnse.kyc.PWDEntity;
import com.cgg.streetvendor.source.reposnse.kyc.PWDResponse;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.QualificationResponse;
import com.cgg.streetvendor.source.reposnse.kyc.RelationEntity;
import com.cgg.streetvendor.source.reposnse.kyc.RelationResponse;
import com.cgg.streetvendor.source.reposnse.kyc.ReligionEntity;
import com.cgg.streetvendor.source.reposnse.kyc.ReligionResponse;
import com.cgg.streetvendor.source.reposnse.kyc.VendingAddressEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingAddressResponse;
import com.cgg.streetvendor.source.reposnse.kyc.VendingTypeEntity;
import com.cgg.streetvendor.source.reposnse.kyc.VendingTypeResponse;
import com.cgg.streetvendor.source.reposnse.places.DistrictEntity;
import com.cgg.streetvendor.source.reposnse.places.DistrictResponse;
import com.cgg.streetvendor.source.reposnse.places.MandalEntity;
import com.cgg.streetvendor.source.reposnse.places.MandalResponse;
import com.cgg.streetvendor.source.reposnse.places.StateEntity;
import com.cgg.streetvendor.source.reposnse.places.StateResponse;
import com.cgg.streetvendor.source.reposnse.places.VillageEntity;
import com.cgg.streetvendor.source.reposnse.places.VillageResponse;
import com.cgg.streetvendor.source.reposnse.ulb.ULBResponse;
import com.cgg.streetvendor.source.reposnse.ulb.UlbEntity;
import com.cgg.streetvendor.source.reposnse.ulb.WardEntity;
import com.cgg.streetvendor.source.reposnse.ulb.WardResponse;
import com.cgg.streetvendor.util.CustomProgressDialog;
import com.cgg.streetvendor.util.ErrorHandler;
import com.cgg.streetvendor.util.Utils;
import com.cgg.streetvendor.viewmodel.SVSSyncBBViewModel;
import com.cgg.streetvendor.viewmodel.SVSSyncKYCViewModel;
import com.cgg.streetvendor.viewmodel.SVSSyncPlaceViewModel;
import com.cgg.streetvendor.viewmodel.SVSSyncULBViewModel;
import com.cgg.streetvendor.viewmodel.SVSSyncVendingViewModel;

import java.util.List;

public class DownloadActivity extends AppCompatActivity implements ErrorHandlerInterface, GCCDivisionInterface {

    SVSSyncPlaceViewModel svsSyncPlaceViewModel;
    SVSSyncBBViewModel svsSyncBBViewModel;
    SVSSyncULBViewModel svsSyncULBViewModel;
    SVSSyncKYCViewModel svsSyncKYCViewModel;
    SVSSyncVendingViewModel svsSyncVendingViewModel;
    private CustomProgressDialog customProgressDialog;
    private SVSSyncPlacesRepository svsSyncPlacesRepository;
    private SVSSyncBBRepository svsSyncBBRepository;
    private SVSSyncULBRepository svsSyncULBRepository;
    private SVSSyncKYCRepository svsSyncKYCRepository;
    private SVSSyncVendingRepository svsSyncVendingRepository;
    private ActivityDownloadBinding binding;

    private String distId, ulbId;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_download);
        sharedPreferences = SVSApplication.get(this).getPreferences();
        distId = sharedPreferences.getString(AppConstants.DISTRICT_ID, "");
        ulbId = sharedPreferences.getString(AppConstants.ULB_ID, "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int startColor = getWindow().getStatusBarColor();
            int endColor = ContextCompat.getColor(this, R.color.colorPrimaryDark);
            ObjectAnimator.ofArgb(getWindow(), "statusBarColor", startColor, endColor).start();
        }

        customProgressDialog = new CustomProgressDialog(this);
        svsSyncPlacesRepository = new SVSSyncPlacesRepository(getApplication());
        svsSyncBBRepository = new SVSSyncBBRepository(getApplication());
        svsSyncULBRepository = new SVSSyncULBRepository(getApplication());
        svsSyncKYCRepository = new SVSSyncKYCRepository(getApplication());
        svsSyncVendingRepository = new SVSSyncVendingRepository(getApplication());
        svsSyncPlaceViewModel = new SVSSyncPlaceViewModel(this, getApplication());
        svsSyncBBViewModel = new SVSSyncBBViewModel(this, getApplication());
        svsSyncULBViewModel = new SVSSyncULBViewModel(this, getApplication());
        svsSyncKYCViewModel = new SVSSyncKYCViewModel(this, getApplication());
        svsSyncVendingViewModel = new SVSSyncVendingViewModel(this, getApplication());

        binding.header.headerTitle.setText(getString(R.string.download_masters));


        LiveData<List<StateEntity>> stateListLiveData = svsSyncPlaceViewModel.getAllStates();
        stateListLiveData.observe(this, new Observer<List<StateEntity>>() {
            @Override
            public void onChanged(List<StateEntity> states) {
                stateListLiveData.removeObservers(DownloadActivity.this);

                if (states == null || states.size() <= 0) {
                    binding.btnPlacesMaster.setText(getString(R.string.download));
                } else {
                    LiveData<List<DistrictEntity>> distListLiveData = svsSyncPlaceViewModel.getAllDistricts();
                    distListLiveData.observe(DownloadActivity.this, new Observer<List<DistrictEntity>>() {
                        @Override
                        public void onChanged(List<DistrictEntity> districtEntities) {
                            distListLiveData.removeObservers(DownloadActivity.this);

                            if (districtEntities == null || districtEntities.size() <= 0) {
                                binding.btnPlacesMaster.setText(getString(R.string.download));
                            } else {
                                LiveData<List<MandalEntity>> manListLiveData = svsSyncPlaceViewModel.getAllMandals();
                                manListLiveData.observe(DownloadActivity.this, new Observer<List<MandalEntity>>() {
                                    @Override
                                    public void onChanged(List<MandalEntity> mandalEntities) {
                                        manListLiveData.removeObservers(DownloadActivity.this);

                                        if (mandalEntities == null || mandalEntities.size() <= 0) {
                                            binding.btnPlacesMaster.setText(getString(R.string.download));
                                        } else {
                                            LiveData<List<VillageEntity>> vilListLiveData = svsSyncPlaceViewModel.getAllVillages();
                                            vilListLiveData.observe(DownloadActivity.this, new Observer<List<VillageEntity>>() {
                                                @Override
                                                public void onChanged(List<VillageEntity> villageEntities) {
                                                    vilListLiveData.removeObservers(DownloadActivity.this);

                                                    if (villageEntities == null || villageEntities.size() <= 0) {
                                                        binding.btnPlacesMaster.setText(getString(R.string.download));
                                                    } else {
                                                        binding.btnPlacesMaster.setText(getString(R.string.re_download));
                                                    }

                                                }
                                            });
                                        }

                                    }
                                });
                            }

                        }
                    });
                }

            }
        });


        LiveData<List<BankEntity>> bankLiveData = svsSyncBBViewModel.getAllBanks();
        bankLiveData.observe(this, new Observer<List<BankEntity>>() {
            @Override
            public void onChanged(List<BankEntity> bankEntities) {
                bankLiveData.removeObservers(DownloadActivity.this);

                if (bankEntities == null || bankEntities.size() <= 0) {
                    binding.btnBankBranch.setText(getString(R.string.download));
                } else {
                    LiveData<List<BranchEntity>> distListLiveData = svsSyncBBViewModel.getAllBranches();
                    distListLiveData.observe(DownloadActivity.this, new Observer<List<BranchEntity>>() {
                        @Override
                        public void onChanged(List<BranchEntity> branchEntities) {
                            distListLiveData.removeObservers(DownloadActivity.this);

                            if (branchEntities == null || branchEntities.size() <= 0) {
                                binding.btnBankBranch.setText(getString(R.string.download));
                            } else {
                                binding.btnBankBranch.setText(getString(R.string.re_download));
                            }

                        }
                    });
                }

            }
        });

        LiveData<List<UlbEntity>> ulbListLiveData = svsSyncULBViewModel.getAllULBs();
        ulbListLiveData.observe(this, new Observer<List<UlbEntity>>() {
            @Override
            public void onChanged(List<UlbEntity> ulbEntities) {
                ulbListLiveData.removeObservers(DownloadActivity.this);

                if (ulbEntities == null || ulbEntities.size() <= 0) {
                    binding.btnUlbMaster.setText(getString(R.string.download));
                } else {
                    LiveData<List<WardEntity>> wListLiveData = svsSyncULBViewModel.getAllWards();
                    wListLiveData.observe(DownloadActivity.this, new Observer<List<WardEntity>>() {
                        @Override
                        public void onChanged(List<WardEntity> wardEntities) {
                            wListLiveData.removeObservers(DownloadActivity.this);

                            if (wardEntities == null || wardEntities.size() <= 0) {
                                binding.btnUlbMaster.setText(getString(R.string.download));
                            } else {
                                binding.btnUlbMaster.setText(getString(R.string.re_download));
                            }

                        }
                    });
                }

            }
        });


        LiveData<List<GenderEntity>> genderListLiveData = svsSyncKYCViewModel.getAllGender();
        genderListLiveData.observe(this, new Observer<List<GenderEntity>>() {
            @Override
            public void onChanged(List<GenderEntity> genderEntities) {
                genderListLiveData.removeObservers(DownloadActivity.this);

                if (genderEntities == null || genderEntities.size() <= 0) {
                    binding.btnKycMaster.setText(getString(R.string.download));
                } else {
                    LiveData<List<CasteEntity>> castListLiveData = svsSyncKYCViewModel.getAllCaste();
                    castListLiveData.observe(DownloadActivity.this, new Observer<List<CasteEntity>>() {
                        @Override
                        public void onChanged(List<CasteEntity> casteEntities) {
                            castListLiveData.removeObservers(DownloadActivity.this);

                            if (casteEntities == null || casteEntities.size() <= 0) {
                                binding.btnKycMaster.setText(getString(R.string.download));
                            } else {
                                LiveData<List<PWDEntity>> pwdListLiveData = svsSyncKYCViewModel.getAllPWDs();
                                pwdListLiveData.observe(DownloadActivity.this, new Observer<List<PWDEntity>>() {
                                    @Override
                                    public void onChanged(List<PWDEntity> pwdEntities) {
                                        pwdListLiveData.removeObservers(DownloadActivity.this);

                                        if (pwdEntities == null || pwdEntities.size() <= 0) {
                                            binding.btnKycMaster.setText(getString(R.string.download));
                                        } else {
                                            LiveData<List<QualificationEntity>> qListLiveData = svsSyncKYCViewModel.getAllQual();
                                            qListLiveData.observe(DownloadActivity.this, new Observer<List<QualificationEntity>>() {
                                                @Override
                                                public void onChanged(List<QualificationEntity> qualificationEntities) {
                                                    qListLiveData.removeObservers(DownloadActivity.this);

                                                    if (qualificationEntities == null || qualificationEntities.size() <= 0) {
                                                        binding.btnKycMaster.setText(getString(R.string.download));
                                                    } else {
                                                        LiveData<List<RelationEntity>> rListLiveData = svsSyncKYCViewModel.getAllRelations();
                                                        rListLiveData.observe(DownloadActivity.this, new Observer<List<RelationEntity>>() {
                                                            @Override
                                                            public void onChanged(List<RelationEntity> relationEntities) {
                                                                rListLiveData.removeObservers(DownloadActivity.this);

                                                                if (relationEntities == null || relationEntities.size() <= 0) {
                                                                    binding.btnKycMaster.setText(getString(R.string.download));
                                                                } else {
                                                                    LiveData<List<ReligionEntity>> rListLiveData = svsSyncKYCViewModel.getAllReligion();
                                                                    rListLiveData.observe(DownloadActivity.this, new Observer<List<ReligionEntity>>() {
                                                                        @Override
                                                                        public void onChanged(List<ReligionEntity> religionEntities) {
                                                                            rListLiveData.removeObservers(DownloadActivity.this);

                                                                            if (religionEntities == null || religionEntities.size() <= 0) {
                                                                                binding.btnKycMaster.setText(getString(R.string.download));
                                                                            } else {
                                                                                binding.btnKycMaster.setText(getString(R.string.re_download));
                                                                            }

                                                                        }
                                                                    });
                                                                }

                                                            }
                                                        });
                                                    }

                                                }
                                            });
                                        }

                                    }
                                });
                            }

                        }
                    });
                }

            }
        });

        LiveData<List<BusinessEntity>> businessListLiveData = svsSyncVendingViewModel.getAllBusiness();
        businessListLiveData.observe(this, new Observer<List<BusinessEntity>>() {
            @Override
            public void onChanged(List<BusinessEntity> businessEntities) {
                businessListLiveData.removeObservers(DownloadActivity.this);

                if (businessEntities == null || businessEntities.size() <= 0) {
                    binding.btnBusinessMaster.setText(getString(R.string.download));
                } else {
                    LiveData<List<VendingTypeEntity>> vListLiveData = svsSyncVendingViewModel.getAllVenTypes();
                    vListLiveData.observe(DownloadActivity.this, new Observer<List<VendingTypeEntity>>() {
                        @Override
                        public void onChanged(List<VendingTypeEntity> vendingTypeEntities) {
                            vListLiveData.removeObservers(DownloadActivity.this);

                            if (vendingTypeEntities == null || vendingTypeEntities.size() <= 0) {
                                binding.btnBusinessMaster.setText(getString(R.string.download));
                            } else {
                                LiveData<List<VendingAddressEntity>> vaListLiveData = svsSyncVendingViewModel.getAllVenAddress();
                                vaListLiveData.observe(DownloadActivity.this, new Observer<List<VendingAddressEntity>>() {
                                    @Override
                                    public void onChanged(List<VendingAddressEntity> vendingAddressEntities) {
                                        vaListLiveData.removeObservers(DownloadActivity.this);

                                        if (vendingAddressEntities == null || vendingAddressEntities.size() <= 0) {
                                            binding.btnBusinessMaster.setText(getString(R.string.download));
                                        } else {
                                            binding.btnBusinessMaster.setText(getString(R.string.re_download));
                                        }

                                    }
                                });
                            }

                        }
                    });
                }

            }
        });


        binding.btnPlacesMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(DownloadActivity.this)) {
                    binding.btnPlacesMaster.setText(getString(R.string.download));
                    customProgressDialog.show();
                    LiveData<StateResponse> officesResponseLiveData = svsSyncPlaceViewModel.getStateResponse();
                    officesResponseLiveData.observe(DownloadActivity.this, new Observer<StateResponse>() {
                        @Override
                        public void onChanged(StateResponse officesResponse) {
                            officesResponseLiveData.removeObservers(DownloadActivity.this);
                            if (officesResponse != null && officesResponse.getStatusCode() != null) {
                                if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (officesResponse.getStateData() != null && officesResponse.getStateData().size() > 0) {
                                        svsSyncPlacesRepository.insertStates(DownloadActivity.this, officesResponse.getStateData());
                                    } else {

                                        Utils.customSuccessAlert(DownloadActivity.this, getString(R.string.app_name),
                                                getString(R.string.something));
                                    }
                                } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    customProgressDialog.hide();
                                    Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                            officesResponse.getStatusMessage());
                                } else {
                                    customProgressDialog.hide();
                                    Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                            getString(R.string.something));
                                }
                            } else {
                                customProgressDialog.hide();
                                Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }
        });

        binding.btnBankBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(DownloadActivity.this)) {
                    customProgressDialog.show();
                    binding.btnBankBranch.setText(getString(R.string.download));
                    LiveData<BankResponse> officesResponseLiveData = svsSyncBBViewModel.getBankResponse();
                    officesResponseLiveData.observe(DownloadActivity.this, new Observer<BankResponse>() {
                        @Override
                        public void onChanged(BankResponse officesResponse) {

                            officesResponseLiveData.removeObservers(DownloadActivity.this);
                            if (officesResponse != null && officesResponse.getStatusCode() != null) {
                                if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (officesResponse.getBankData() != null && officesResponse.getBankData().size() > 0) {
                                        svsSyncBBRepository.insertBanks(DownloadActivity.this, officesResponse.getBankData());
                                    } else {
                                        customProgressDialog.hide();

                                        Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                                getString(R.string.something));
                                    }
                                } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    customProgressDialog.hide();
                                    Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                            officesResponse.getStatusMessage());
                                } else {
                                    customProgressDialog.hide();
                                    Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                            getString(R.string.something));
                                }
                            } else {
                                customProgressDialog.hide();
                                Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }
        });

        binding.btnUlbMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(DownloadActivity.this)) {
                    customProgressDialog.show();
                    binding.btnUlbMaster.setText(getString(R.string.download));
                    LiveData<ULBResponse> officesResponseLiveData = svsSyncULBViewModel.getULBResponse();
                    officesResponseLiveData.observe(DownloadActivity.this, new Observer<ULBResponse>() {
                        @Override
                        public void onChanged(ULBResponse officesResponse) {

                            officesResponseLiveData.removeObservers(DownloadActivity.this);
                            if (officesResponse != null && officesResponse.getStatusCode() != null) {
                                if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (officesResponse.getUlbData() != null && officesResponse.getUlbData().size() > 0) {
                                        svsSyncULBRepository.insertULBs(DownloadActivity.this, officesResponse.getUlbData());
                                    } else {
                                        customProgressDialog.hide();

                                        Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                                getString(R.string.something));
                                    }
                                } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    customProgressDialog.hide();
                                    Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                            officesResponse.getStatusMessage());
                                } else {
                                    customProgressDialog.hide();
                                    Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                            getString(R.string.something));
                                }
                            } else {
                                customProgressDialog.hide();
                                Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }
        });

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnKycMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(DownloadActivity.this)) {
                    customProgressDialog.show();
                    binding.btnKycMaster.setText(getString(R.string.download));
                    LiveData<GenderResponse> officesResponseLiveData = svsSyncKYCViewModel.getGenderResponse();
                    officesResponseLiveData.observe(DownloadActivity.this, new Observer<GenderResponse>() {
                        @Override
                        public void onChanged(GenderResponse officesResponse) {

                            officesResponseLiveData.removeObservers(DownloadActivity.this);
                            if (officesResponse != null && officesResponse.getStatusCode() != null) {
                                if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (officesResponse.getGenderData() != null && officesResponse.getGenderData().size() > 0) {
                                        svsSyncKYCRepository.insertGender(DownloadActivity.this, officesResponse.getGenderData());
                                    } else {
                                        customProgressDialog.hide();

                                        Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                                getString(R.string.something));
                                    }
                                } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    customProgressDialog.hide();
                                    Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                            officesResponse.getStatusMessage());
                                } else {
                                    customProgressDialog.hide();
                                    Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                            getString(R.string.something));
                                }
                            } else {
                                customProgressDialog.hide();
                                Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }
        });

        binding.btnBusinessMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(DownloadActivity.this)) {
                    customProgressDialog.show();
                    binding.btnBusinessMaster.setText(getString(R.string.download));
                    LiveData<BusinessResponse> officesResponseLiveData = svsSyncVendingViewModel.getBusinessResponse();
                    officesResponseLiveData.observe(DownloadActivity.this, new Observer<BusinessResponse>() {
                        @Override
                        public void onChanged(BusinessResponse officesResponse) {

                            officesResponseLiveData.removeObservers(DownloadActivity.this);
                            if (officesResponse != null && officesResponse.getStatusCode() != null) {
                                if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                    if (officesResponse.getBranchData() != null && officesResponse.getBranchData().size() > 0) {
                                        svsSyncVendingRepository.insertBusiness(DownloadActivity.this, officesResponse.getBranchData());
                                    } else {
                                        customProgressDialog.hide();

                                        Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                                getString(R.string.something));
                                    }
                                } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                    customProgressDialog.hide();
                                    Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                            officesResponse.getStatusMessage());
                                } else {
                                    customProgressDialog.hide();

                                    Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                            getString(R.string.something));
                                }
                            } else {
                                customProgressDialog.hide();

                                Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        }
                    });
                } else {
                    Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                }
            }
        });

    }

    private void callDistrictAPI(String stateID) {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<DistrictResponse> officesResponseLiveData = svsSyncPlaceViewModel.getDistrictResponse(stateID);
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<DistrictResponse>() {
                @Override
                public void onChanged(DistrictResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getDistrictData() != null && officesResponse.getDistrictData().size() > 0) {
                                svsSyncPlacesRepository.insertDistricts(DownloadActivity.this, officesResponse.getDistrictData());

                            } else {
                                customProgressDialog.hide();


                                Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();

                            Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();

                            Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();

                        Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }


    private void callMandalAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<MandalResponse> officesResponseLiveData = svsSyncPlaceViewModel.getMandalResponse();
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<MandalResponse>() {
                @Override
                public void onChanged(MandalResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getMandalData() != null && officesResponse.getMandalData().size() > 0) {
                                svsSyncPlacesRepository.insertMandals(DownloadActivity.this, officesResponse.getMandalData());

                            } else {
                                customProgressDialog.hide();


                                Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();

                            Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();

                            Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();

                        Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }

    private void callVillageAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<VillageResponse> officesResponseLiveData = svsSyncPlaceViewModel.getVillageResponse();
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<VillageResponse>() {
                @Override
                public void onChanged(VillageResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getVillageData() != null && officesResponse.getVillageData().size() > 0) {
                                svsSyncPlacesRepository.insertVillages(DownloadActivity.this, officesResponse.getVillageData());

                            } else {
                                customProgressDialog.hide();


                                Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();

                            Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();

                            Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();

                        Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }


    private void callBranchAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<BranchResponse> officesResponseLiveData = svsSyncBBViewModel.getBranchResponse();
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<BranchResponse>() {
                @Override
                public void onChanged(BranchResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getBranchData() != null && officesResponse.getBranchData().size() > 0) {
                                svsSyncBBRepository.insertBranches(DownloadActivity.this, officesResponse.getBranchData());

                            } else {
                                customProgressDialog.hide();


                                Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();

                            Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();

                            Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();

                        Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }


    private void callWardAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<WardResponse> officesResponseLiveData = svsSyncULBViewModel.getWardResponse();
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<WardResponse>() {
                @Override
                public void onChanged(WardResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getWardData() != null && officesResponse.getWardData().size() > 0) {
                                svsSyncULBRepository.insertWards(DownloadActivity.this, officesResponse.getWardData());

                            } else {
                                customProgressDialog.hide();

                                Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();
                        Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }

    private void callCasteAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<CasteResponse> officesResponseLiveData = svsSyncKYCViewModel.getCasteResponse();
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<CasteResponse>() {
                @Override
                public void onChanged(CasteResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getCasteData() != null && officesResponse.getCasteData().size() > 0) {
                                svsSyncKYCRepository.insertCaste(DownloadActivity.this, officesResponse.getCasteData());

                            } else {
                                customProgressDialog.hide();
                                Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();
                        Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }

    private void callPWDAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<PWDResponse> officesResponseLiveData = svsSyncKYCViewModel.getPWDResponse();
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<PWDResponse>() {
                @Override
                public void onChanged(PWDResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getPwdData() != null && officesResponse.getPwdData().size() > 0) {
                                svsSyncKYCRepository.insertPWD(DownloadActivity.this, officesResponse.getPwdData());

                            } else {
                                customProgressDialog.hide();
                                Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    officesResponse.getStatusCode());
                        } else {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();
                        Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }

    private void callQualAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<QualificationResponse> officesResponseLiveData = svsSyncKYCViewModel.getQuaResponse();
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<QualificationResponse>() {
                @Override
                public void onChanged(QualificationResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getQualificationData() != null && officesResponse.getQualificationData().size() > 0) {
                                svsSyncKYCRepository.insertQualification(DownloadActivity.this, officesResponse.getQualificationData());

                            } else {
                                customProgressDialog.hide();
                                Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();
                        Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }


    private void callRelationAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<RelationResponse> officesResponseLiveData = svsSyncKYCViewModel.getRelationResponse();
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<RelationResponse>() {
                @Override
                public void onChanged(RelationResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getRelationData() != null && officesResponse.getRelationData().size() > 0) {
                                svsSyncKYCRepository.insertRelation(DownloadActivity.this, officesResponse.getRelationData());

                            } else {
                                customProgressDialog.hide();
                                Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();
                        Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }


    private void callReligionAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<ReligionResponse> officesResponseLiveData = svsSyncKYCViewModel.getReligionResponse();
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<ReligionResponse>() {
                @Override
                public void onChanged(ReligionResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getReligionData() != null && officesResponse.getReligionData().size() > 0) {
                                svsSyncKYCRepository.insertReligion(DownloadActivity.this, officesResponse.getReligionData());

                            } else {
                                customProgressDialog.hide();
                                Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();
                        Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }


    private void callVenTypeAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<VendingTypeResponse> officesResponseLiveData = svsSyncVendingViewModel.getVenTypeResponse();
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<VendingTypeResponse>() {
                @Override
                public void onChanged(VendingTypeResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getVendingData() != null && officesResponse.getVendingData().size() > 0) {
                                svsSyncVendingRepository.insertVenTypes(DownloadActivity.this, officesResponse.getVendingData());

                            } else {

                                Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();
                        Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }

    private void callVenAddressAPI() {
        if (Utils.checkInternetConnection(DownloadActivity.this)) {

            LiveData<VendingAddressResponse> officesResponseLiveData = svsSyncVendingViewModel.getVenAddressResponse("districtID="+"0"+"&"+"ulbID="+"0");
            officesResponseLiveData.observe(DownloadActivity.this, new Observer<VendingAddressResponse>() {
                @Override
                public void onChanged(VendingAddressResponse officesResponse) {

                    officesResponseLiveData.removeObservers(DownloadActivity.this);
                    if (officesResponse != null && officesResponse.getStatusCode() != null) {
                        if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                            if (officesResponse.getVillageData() != null && officesResponse.getVillageData().size() > 0) {
                                svsSyncVendingRepository.insertVenAddress(DownloadActivity.this, officesResponse.getVillageData());

                            } else {
                                customProgressDialog.hide();

                                Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                        getString(R.string.something));
                            }
                        } else if (officesResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    officesResponse.getStatusMessage());
                        } else {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                    getString(R.string.something));
                        }
                    } else {
                        customProgressDialog.hide();
                        Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                                getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }


    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                errMsg);
    }

    @Override
    public void stateCount(int count) {
        if (count > 1) {
            callDistrictAPI("36");
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_states));
        }
    }

    @Override
    public void distCount(int count) {
        if (count > 1) {
            callMandalAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_districts));
        }
    }

    @Override
    public void manCount(int count) {
        if (count > 1) {
            callVillageAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_mandals));
        }
    }

    @Override
    public void vilCount(int count) {
        customProgressDialog.hide();
        if (count > 1) {
            binding.btnPlacesMaster.setText(getString(R.string.re_download));
            // Success Alert
            Utils.customSuccessAlert(DownloadActivity.this, getString(R.string.app_name),
                    getString(R.string.places_message_succcess));
        } else {

            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_villages));
        }
    }

    @Override
    public void bankCount(int count) {
        if (count > 1) {
            callBranchAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_banks));
        }
    }

    @Override
    public void branchCount(int count) {
        customProgressDialog.hide();
        if (count > 1) {
            binding.btnBankBranch.setText(getString(R.string.re_download));
            Utils.customSuccessAlert(DownloadActivity.this, getString(R.string.app_name),
                    getString(R.string.bb_message_success));
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_branches));
        }
    }

    @Override
    public void ulbCount(int count) {
        if (count > 1) {
            callWardAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_ulb));
        }
    }

    @Override
    public void wardCount(int count) {
        customProgressDialog.hide();
        if (count > 1) {
            binding.btnUlbMaster.setText(getString(R.string.re_download));
            Utils.customSuccessAlert(DownloadActivity.this, getString(R.string.app_name),
                    getString(R.string.ulb_message_success));
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_ward));
        }
    }

    @Override
    public void genderCount(int count) {
        if (count > 1) {
            callCasteAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_gender));
        }
    }

    @Override
    public void religionCount(int count) {
        customProgressDialog.hide();
        if (count > 1) {
            binding.btnKycMaster.setText(getString(R.string.re_download));
            Utils.customSuccessAlert(DownloadActivity.this, getString(R.string.app_name),
                    getString(R.string.kyc_message_success));
        } else {
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_religion));
        }
    }

    @Override
    public void casteCount(int count) {
        if (count > 1) {
            callPWDAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_catse));
        }
    }

    @Override
    public void pwdCount(int count) {
        if (count > 1) {
            callQualAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_pwd_data));
        }
    }

    @Override
    public void quaCount(int count) {
        if (count > 1) {
            callRelationAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_qual));
        }
    }

    @Override
    public void relCount(int count) {
        customProgressDialog.hide();
        if (count > 1) {
           callReligionAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_relation));
        }
    }

    @Override
    public void businessCount(int count) {
        if (count > 1) {
            callVenTypeAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_business));
        }
    }

    @Override
    public void venTypeCount(int count) {
        if (count > 1) {
            callVenAddressAPI();
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_ven_types));
        }
    }

    @Override
    public void venAddCount(int count) {
        customProgressDialog.hide();
        if (count > 1) {
            binding.btnBusinessMaster.setText(getString(R.string.re_download));
            Utils.customSuccessAlert(DownloadActivity.this, getString(R.string.app_name),
                    getString(R.string.vending_message_success));
        } else {
            customProgressDialog.hide();
            Utils.customErrorAlert(DownloadActivity.this, getResources().getString(R.string.app_name),
                    getString(R.string.no_ven_area));
        }
    }
}

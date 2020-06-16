package com.cgg.streetvendor.interfaces;

import com.cgg.streetvendor.ui.FamilyInfo;

public interface MemberInterface {
    public void editMember(FamilyInfo familyInfo, int pos);
    public void removeMember(int pos);
}

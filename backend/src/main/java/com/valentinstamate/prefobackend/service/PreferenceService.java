package com.valentinstamate.prefobackend.service;

import com.valentinstamate.prefobackend.persistence.models.PreferenceModel;
import com.valentinstamate.prefobackend.persistence.models.UserModel;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class PreferenceService {

    public List<PreferenceModel> getStudentSorterPreferences(UserModel userModel) {
        var preferences = userModel.getPreferenceModels();

        preferences.sort(Comparator
                .comparing((PreferenceModel itemA) -> itemA.get_class().getClassPackage())
                .thenComparingInt(PreferenceModel::getPriority));

        return new ArrayList<>(preferences);
    }

}

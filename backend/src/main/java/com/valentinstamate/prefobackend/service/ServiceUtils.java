package com.valentinstamate.prefobackend.service;

import com.valentinstamate.prefobackend.persistence.models.PreferenceModel;

import java.util.*;
import java.util.stream.Collectors;

public class ServiceUtils {

    public static Map<String, Queue<Object>> orderPreferences(List<PreferenceModel> preferences, int packagesNumber) {
        var map = new HashMap<String, PriorityQueue<PreferenceModel>>();

        for (int i = 1; i <= packagesNumber; i++) {
            map.put(String.format("CO%d", i), new PriorityQueue<>(Comparator.comparing(PreferenceModel::getPriority)));
        }

        for (var preference : preferences) {
            var _class = preference.get_class();
            var packageName = _class.getClassPackage();

            map.get(packageName).add(preference);
        }

        var orderPackageMap = new HashMap<String, Queue<Object>>();
        var sortedPackages = map.keySet().stream().sorted().collect(Collectors.toList());

        for (var packageName : sortedPackages) {
            var orderedPackageList = new LinkedList<>();
            orderPackageMap.put(packageName, orderedPackageList);

            var orderedPreferences = map.get(packageName);

            while (!orderedPreferences.isEmpty()) {
                var preference = orderedPreferences.poll();
                var _class = preference.get_class();

                orderedPackageList.add(_class.getShortName());
            }

        }

        return orderPackageMap;
    }

}

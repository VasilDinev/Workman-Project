package org.softuni.workman.service;

import org.softuni.workman.domain.models.service.WorkmanServiceModel;

import java.util.List;

public interface  WorkmanService {

    WorkmanServiceModel createWorkman(WorkmanServiceModel workmanServiceModel);

    WorkmanServiceModel findWorkmanById (String id);
    WorkmanServiceModel editWorkman(String id, WorkmanServiceModel workmanServiceModel);
    WorkmanServiceModel deleteWorkman(String id);

    List<WorkmanServiceModel> findAllWorkman();
}

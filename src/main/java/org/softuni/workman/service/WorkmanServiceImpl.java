package org.softuni.workman.service;

import org.modelmapper.ModelMapper;
import org.softuni.workman.domain.entities.Workman;
import org.softuni.workman.domain.models.service.WorkmanServiceModel;
import org.softuni.workman.error.WorkmanNameAlreadyExistsException;
import org.softuni.workman.error.WorkmanNotFoundException;
import org.softuni.workman.repository.WorkmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkmanServiceImpl implements WorkmanService {

    private final WorkmanRepository workmanRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public WorkmanServiceImpl(WorkmanRepository workmanRepository,  ModelMapper modelMapper) {
        this.workmanRepository = workmanRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public WorkmanServiceModel createWorkman(WorkmanServiceModel workmanServiceModel) {
        Workman workman= this.workmanRepository.getByNames(workmanServiceModel.getFirstName(),workmanServiceModel.getFirstName()).orElse(null);

        if (workman != null) {
            throw new WorkmanNameAlreadyExistsException("Workman already exists");
        }
        workman=this.modelMapper.map(workmanServiceModel,Workman.class);

        workman=this.workmanRepository.save(workman);
        return this.modelMapper.map(workman,WorkmanServiceModel.class);
    }

    @Override
    public WorkmanServiceModel findWorkmanById(String id) {
        return this.workmanRepository.findById(id)
                .map(w -> {
                    WorkmanServiceModel workmanServiceModel = this.modelMapper.map(w, WorkmanServiceModel.class);


                    return workmanServiceModel;
                })
                .orElseThrow(() -> new WorkmanNotFoundException("Workman with the given id was not found!"));
    }

    @Override
    public WorkmanServiceModel editWorkman(String id, WorkmanServiceModel workmanServiceModel) {
        Workman workman = this.workmanRepository.findById(id)
                .orElseThrow(() -> new WorkmanNotFoundException("Workman with the given id was not found!"));
        workman.setFirstName(workmanServiceModel.getFirstName());
        workman.setLastName(workmanServiceModel.getLastName());
        workman.setEmail(workmanServiceModel.getEmail());
        workman.setPhoneNumber(workmanServiceModel.getPhoneNumber());
        workman.setJobFunction(workmanServiceModel.getJobFunction());


        return this.modelMapper.map(this.workmanRepository.saveAndFlush(workman), WorkmanServiceModel.class);
    }

    @Override
    public WorkmanServiceModel deleteWorkman(String id) {
        Workman workman = this.workmanRepository.findById(id).orElseThrow(() -> new WorkmanNotFoundException("Workman with the given id was not found!"));

         this.workmanRepository.delete(workman);

         return this.modelMapper.map(workman, WorkmanServiceModel.class);
    }

    @Override
    public List<WorkmanServiceModel> findAllWorkman() {
        return this.workmanRepository.findAll().stream().map(w->this.modelMapper.map(w,WorkmanServiceModel.class)).collect(Collectors.toList());
    }

}

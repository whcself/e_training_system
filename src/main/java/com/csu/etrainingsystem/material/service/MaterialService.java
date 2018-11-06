package com.csu.etrainingsystem.material.service;
import com.csu.etrainingsystem.material.entity.Material;
import com.csu.etrainingsystem.material.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Transactional
    public void addMaterial_apply(Material material) {
        this.materialRepository.save(material);
    }
    @Transactional
    public Material getMaterial(String clazz) {
        Optional<Material> material=materialRepository.findMaterialByClazz(clazz);
        return material.get();
    }
    @Transactional
    public Iterable<Material> getAllMaterial() {
        return this.materialRepository.findAllMaterial();
    }

    @Transactional
    public void  updateMaterial(Material Material) {
        this.materialRepository.saveAndFlush(Material);
    }
    @Transactional
    public void  deleteMaterial(String clazz) {

       /*
       todo:消除删除一个物料种类所带来的影响,这个需要悬空吗?
       即:购买表的记录应该还是需要有
        */
    }

}

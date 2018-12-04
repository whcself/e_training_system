package com.csu.etrainingsystem.material.service;
import com.csu.etrainingsystem.material.entity.Apply;
import com.csu.etrainingsystem.material.entity.Material;
import com.csu.etrainingsystem.material.repository.ApplyRepository;
import com.csu.etrainingsystem.material.repository.MaterialRepository;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final ApplyRepository applyRepository;
    @Autowired
    public MaterialService(MaterialRepository materialRepository, ApplyRepository applyRepository) {
        this.materialRepository = materialRepository;
        this.applyRepository = applyRepository;
    }

    @Transactional
    public void addMaterial_apply(Material material) {
        this.materialRepository.save(material);
    }
    @Transactional
    public Material getMaterial(String clazz) {
        Material material=materialRepository.findMaterialByClazz(clazz);

        return material;
    }
    @Transactional
    public Iterable<Material> getAllMaterial() {
        return this.materialRepository.findAllMaterial();
    }
    @Transactional
    public Iterable<Material> getMaterialNotEmpty() {
        return this.materialRepository.findMaterialNotEmpty ();
    }


    @Transactional
    public void  addMaterial(Material Material) {
        this.materialRepository.save(Material);
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
    @Transactional
    public Iterable<Apply> getAplyBySidAndSnameAndClazzAndTime(String sid, String sname , String clazz, Date startTime, Date endTime) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String start="";
        String end="";
        if(startTime!=null)start = format.format (startTime);
        else start= format.format(applyRepository.findMinTime ().get (0));
        if(endTime!=null) end= format.format (endTime);
        else end= format.format(applyRepository.findMaxTime ().get (0));
        if(sid==null)sid="%";if(sname==null)sname="%";if(clazz==null)clazz="%";
        System.out.println (sid+sname+clazz);
        return this.applyRepository.findAplyBySidAndSnameAndClazzAndTime(sid, sname ,clazz, start, end);
    }
    @Transactional
    public void addAply(Apply apply) {
        this.applyRepository.save (apply);
       }

}

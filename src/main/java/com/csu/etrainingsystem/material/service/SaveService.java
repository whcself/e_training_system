package com.csu.etrainingsystem.material.service;

import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Material;
import com.csu.etrainingsystem.material.entity.Save;
import com.csu.etrainingsystem.material.repository.MaterialRepository;
import com.csu.etrainingsystem.material.repository.PurchaseRepository;
import com.csu.etrainingsystem.material.repository.SaveRepository;
import com.csu.etrainingsystem.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SaveService {
    private final SaveRepository saveRepository;
    private final PurchaseRepository purchaseRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public SaveService(SaveRepository saveRepository, PurchaseRepository purchaseRepository, MaterialRepository materialRepository) {
        this.saveRepository = saveRepository;
        this.purchaseRepository = purchaseRepository;
        this.materialRepository = materialRepository;
    }


    public List<Save> getSaveBy5(String begin,
                                 String end,
                                 String clazz,
                                 String tname,
                                 String pid) {
        return saveRepository.findBy5(begin, end, clazz, tname, pid);

    }

    @Transactional
    public CommonResponseForm addSave(String pid,
                                      String num,
                                      String tname,
                                      String remark) {
        Integer remain=saveRepository.remainSave(pid);
        remain=remain==null?0:remain;
        if (remain + Integer.valueOf(num) > purchaseRepository.getAllPurNum(pid)) {
            return CommonResponseForm.of400("入库失败，入库数量大于采购数量");
        }
        String clazz = purchaseRepository.getClazzByPId(pid);
        Save save = new Save();
        save.setPurchase_id(pid);
        save.setSave_num(Integer.valueOf(num));
        save.setSave_tname(tname);
        save.setSave_remark(remark);
        save.setClazz(clazz);
        save.setSave_time(TimeUtil.getNowDate());
        save.setDel_status(false);
        saveRepository.save(save);

        Integer numOfMaterial = saveRepository.getNumofMaterial(clazz);
        //如果没有则需新增
        if (numOfMaterial == null) {

            Material material = new Material(clazz, Integer.valueOf(num), false);
            materialRepository.save(material);
        } else {
            saveRepository.saveToStorage(Integer.parseInt(num), clazz);
        }
        return CommonResponseForm.of204("入库成功");
    }


}

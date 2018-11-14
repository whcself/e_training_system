package com.csu.etrainingsystem.material.controller;


import com.csu.etrainingsystem.experiment.entity.Experiment;
import com.csu.etrainingsystem.form.CommonResponseForm;
import com.csu.etrainingsystem.material.entity.Material;
import com.csu.etrainingsystem.material.service.MaterialService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "material",method = RequestMethod.POST)
public class MaterialController {
    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @ApiOperation (value = "查询所有物料")
    @RequestMapping(value ="/getAllMaterial")
    public CommonResponseForm getAllMaterial(){
        return CommonResponseForm.of200 ("获取物料成功",this.materialService.getAllMaterial ());
    }
    @ApiOperation (value = "派出物料")
    @RequestMapping(value ="/decrMaterialNum")
    public CommonResponseForm decrMaterialNum(@RequestParam(required = false) int num,
                                              @RequestParam String clazz){

       Material material= this.materialService.getMaterial (clazz);
       material.setNum (material.getNum ()-num);
       this.materialService.updateMaterial (material);
        return CommonResponseForm.of204 ("派出物料成功");
    }
    @ApiOperation (value = "补充物料(在申购通过之后)")
    @RequestMapping(value ="/increMaterialNum")
    public CommonResponseForm increMaterialNum(@RequestParam(required = false) int num,
                                              @RequestParam String clazz){

        Material material= this.materialService.getMaterial (clazz);
        material.setNum (material.getNum ()+num);
        this.materialService.updateMaterial (material);
        return CommonResponseForm.of204 ("补充物料成功");
    }

}

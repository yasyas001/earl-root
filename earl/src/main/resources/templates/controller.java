package ${package.Controller};

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yihaodian.scm.core.inject.InjectNameUtil;
import com.yihaodian.scm.core.bean.Page;
import com.logc.scm.framework.common.web.AjaxReturn;
import org.springframework.ui.Model;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
#if(${superControllerClassPackage})
import ${superControllerClassPackage};
#end

/**
 * <p>
 * $!{table.comment} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Controller
@RequestMapping("#if(${package.ModuleName})/${package.ModuleName}#end/${table.entityPath}")
#if(${superControllerClass})
public class ${table.controllerName} extends ${superControllerClass} {
#else
public class ${table.controllerName} {
#end

    @Autowired private ${table.serviceName} ${table.entityPath}Service;
    
    @GetMapping("/list")
    public String manager() {
        return "modules/${table.entityPath}/${table.entityPath}List";
    }

    @PostMapping("/listData")
    @ResponseBody
    public Page<${entity}> listData(${entity} ${table.entityPath}, HttpServletRequest request, HttpServletResponse response) {
        Page<${entity}> page = ${table.entityPath}Service._findPage(${table.entityPath}.getPage(), ${table.entityPath});
        page.setList(InjectNameUtil.inject(page.getList()));
        return page;
    }

    /**
     * 编辑
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/form")
    @ResponseBody
    public Object form(Model model, Long id) {
        ${entity} ${table.entityPath} = ${table.entityPath}Service._selectById(id);
        return InjectNameUtil.inject(${table.entityPath});
    }
    
    /**
     * 保存
     * @param 
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public void save(@Valid ${entity} ${table.entityPath}) {
        ${table.entityPath}Service.insertOrUpdate(${table.entityPath});
    }
    
    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public void delete(${entity} ${table.entityPath}) {
        ${table.entityPath}Service._delete(${table.entityPath});
    }

}

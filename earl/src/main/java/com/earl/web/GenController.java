package com.earl.web;

import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.earl.Tools.DBUtils;
import com.earl.Tools.Page;
import com.earl.util.GenConfig;
import com.earl.util.GenerateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/a/sys/gen")
public class GenController{



    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/listData")
    public Page list(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        //查询列表数据
        Page page = new Page(Integer.valueOf(params.get("pageNo").toString()).intValue(),Integer.valueOf(params.get("pageSize").toString()).intValue());//sysGeneratorService.findPage(new Page(request),params);


        String url = "jdbc:mysql://"+params.get("ip")+"?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&tinyInt1isBit=false";
        DBUtils dbUtils = new DBUtils(url, params.get("username").toString(), params.get("password").toString());

        String sql = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables "+
                "where table_schema = (select database()) ";
        Object s = params.get("tableName");

        if(s!=null && StringUtils.isNotBlank(s.toString())){
            sql+="and table_name like concat('%', '"+s.toString()+"', '%')";
        }

        Integer pageNo = page.getPageNo()-1;
        Integer pageSize = page.getPageSize();
        pageNo = pageNo <= 0 ? 0 : pageNo * pageSize;
        sql+=" limit "+pageNo+", "+pageSize;
        List<Map<String, Object>> list = dbUtils.selectList(sql);

        page.setList(list);

        sql = "select count(*) from information_schema.tables where table_schema = (select database()) ";
        if(s!=null && StringUtils.isNotBlank(s.toString())){
            sql+="and table_name like concat('%', '"+s.toString()+"', '%')";
        }
        Long integer = dbUtils.selectCount(sql);
        page.setCount(integer);
        dbUtils.close();

        return page;
    }

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/queryColumns")
    public List queryColumns(HttpServletRequest request, @RequestBody Map<String, Object> params) {
//        RouterManager.dbFlag.set(params.get("defFlag").toString());

        String sql = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns " +
                "  where table_name = '"+params.get("tableName").toString()+"' and table_schema = (select database()) order by ordinal_position";
        String url = "jdbc:mysql://"+params.get("ip")+"?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&tinyInt1isBit=false";
        DBUtils dbUtils = new DBUtils(url, params.get("username").toString(), params.get("password").toString());
        List<Map<String, Object>> maps = dbUtils.selectList(sql);
        List<String> strings = Arrays.asList(new String[]{"id", "create_date", "create_by", "update_date", "update_by", "version", "del_flag", "company_id"});
        for (Map map : maps) {
            String columnName = map.get("columnName").toString();
            map.put("isSearch",!strings.contains(columnName));
            map.put("isEdit",!strings.contains(columnName));
            map.put("isTable",!strings.contains(columnName));
            columnName = columnToJava(columnName);
            map.put("key",columnName);
            Object columnComment = map.get("columnComment");
            map.put("name", columnComment!=null && StringUtils.isNotBlank(columnComment.toString()) ? columnComment.toString() : columnName);

        }
        dbUtils.close();

        return maps;
    }

    public String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }
    /**
     * 生成代码
     */
    @RequestMapping("/code")
    @ResponseBody
    public String code(HttpServletRequest request, HttpServletResponse response, @RequestBody GenConfig genConfig) throws IOException {
//        RouterManager.dbFlag.set(genConfig.getUrl());

//        DruidDataSource bean = ApplicationContextHolder.getBean(RouterManager.dbFlag.get(), DruidDataSource.class);
//        genConfig.setUrl(bean.getUrl());
//        genConfig.setUsername(bean.getUsername());
//        genConfig.setPassword(bean.getPassword());

        String url = "jdbc:mysql://"+genConfig.getUrl()+"?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&tinyInt1isBit=false";
        genConfig.setUrl(url);

        int i = (int) (Math.random() * 1000000);
        String srcPath = GenerateUtil.class.getResource("/").getPath() + "genTemplates/";
        String zipPath = srcPath + i + "_" + genConfig.getTableName() + ".zip";
        String genPath = srcPath+i;
        File file = new File(genPath+"/"+genConfig.getTableName());
        if(!file.isDirectory()){
            file.mkdirs();
        }
        genConfig.setOutputDir(genPath+"/"+genConfig.getTableName());

        BufferedWriter bufferedWriter = null;

        try {
            GenerateUtil.generate(genConfig);

            File jsonCofig = new File(genPath + "/" + genConfig.getTableName() + "/"+genConfig.getTableName()+"_configJson.json");
            if(!jsonCofig.isFile()){
                jsonCofig.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new FileWriter(jsonCofig));
            String configJson = JSON.toJSONString(genConfig, SerializerFeature.WriteDateUseDateFormat);
            bufferedWriter.write(configJson);
            bufferedWriter.close();


            File zip = ZipUtil.zip(genPath+"/"+ genConfig.getTableName() + "/", zipPath, Charset.forName("utf-8"),true);
            zipPath = zip.getPath();


        }catch (Exception e){
            zipPath = null;
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(bufferedWriter);
            FileUtils.deleteDirectory(new File(genPath));

        }
        return zipPath;

    }

    @RequestMapping("/downGen")
    public void downGen(HttpServletRequest request, HttpServletResponse response, String zipPath) throws IOException {
        byte[] data = {};
//        try {
        File file = new File(zipPath);
        data = FileUtils.readFileToByteArray(file);

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"AutoCode"
                    + file.getName() + "\"");
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        IOUtils.write(data, response.getOutputStream());

//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//
//        }
    }


}

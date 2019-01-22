package com.csu.etrainingsystem.util;

import com.csu.etrainingsystem.experiment.repository.ExperimentRepository;
import com.csu.etrainingsystem.procedure.repository.ProcedureRepository;
import com.csu.etrainingsystem.score.repository.ScoreRepository;
import com.csu.etrainingsystem.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.transaction.Transactional;

@Component
public class InsertUtil {

    private static ScoreRepository scoreRepository;
    private StudentRepository studentRepository;
    private ProcedureRepository procedureRepository;
    private ExperimentRepository experimentRepository;
    private static JdbcTemplate jdbcTemplate;


    @Autowired
    public InsertUtil(ScoreRepository scoreRepository, StudentRepository studentRepository, ProcedureRepository procedureRepository, ExperimentRepository experimentRepository) {
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
        this.procedureRepository = procedureRepository;
        this.experimentRepository = experimentRepository;
    }

    /**
     * insert score items including 1,2,5 credit ,
     * need to make sure you have the sid, suggest call it with insertStudent (the same parameters)
     *
     * @param begin the begin of the no of sid
     * @param end   the end
     */
    private static void insertScore(int begin, int end) {
        for (int i = begin; i < end; i++) {
            String[] sqls = {
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','1" + i + "', '铸造', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','1" + i + "', '数控线切割', '88');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','1" + i + "', '焊接', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','1" + i + "', '逆向工程快速原型', '77');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','1" + i + "', '锻压', '77');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','1" + i + "', '铣削', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','1" + i + "', '磨削', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','1" + i + "', '车削', '48');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','1" + i + "', '钳工', '48');",
                    //2`
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '铸造', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '数控线切割', '88');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', 'Cimatron', '88');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '激光切割', '88');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '加工中心', '88');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '焊接', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '逆向工程快速原型', '77');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '热处理', '77');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '车削', '48');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '锻造', '90');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '铣削', '48');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '磨削', '48');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','2" + i + "', '钳工', '89');",
                    // 5`
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '铸造', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '数控线切割', '88');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', 'Cimatron', '88');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '激光切割', '88');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '加工中心', '88');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '焊接', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '激光焊接', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '逆向工程快速原型', '77');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '热处理', '77');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '车削', '48');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '锻造', '90');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '铣削', '48');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '磨削', '48');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '钳工', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '数控车', '89');",
                    "INSERT INTO `etc2`.`score` (`tid`,`sid`, `pro_name`, `pro_score`) VALUES ('111111','3" + i + "', '数控车仿真', '89');",

            };

            for (String sql : sqls) {
                jdbcTemplate.execute(sql);
            }
        }
    }

    /**
     * @param begin 开始的编号尾数
     * @param end   结束的编号尾数
     */
    private static void insertStudent(int begin, int end) {
        System.out.println(begin + " " + end);
        for (int i = begin; i < end; i++) {
            String[] sqls = {
                    "INSERT INTO `etc2`.`student` (`sid`, `batch_name`, `sname`, `clazz`) VALUES ('0101" + i + "', '2019S101', '刘一" + i + "', '自动化1601');",
                    "INSERT INTO `etc2`.`student` (`sid`, `batch_name`, `sname`, `clazz`) VALUES ('0102" + i + "', '2019S201', '刘二" + i + "', '自动化1601');",
                    "INSERT INTO `etc2`.`student` (`sid`, `batch_name`, `sname`, `clazz`) VALUES ('0103" + i + "', '2019S501', '刘五" + i + "', '自动化1601');",

            };

            for (String sql : sqls) {
                jdbcTemplate.execute(sql);
            }
        }
    }

    private static void insertProcedConn() {
        String[] sqls = {
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('逆向工程快速原型', '逆向组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('热处理', '热处理组', 'conn', '0.1')"};

        String[] sqls2 = {
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('铸造', '铸造组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('数控线切割', '数控线切割组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('Cimatron', 'Cimatron组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('锻造', '锻造组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('激光切割', '激光组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('加工中心', '加工组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('焊接', '焊接组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('激光焊接', '激光焊接组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('车削', '车削组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('数控车', '管理组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('数控仿真车', '管理组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('钳工', '钳工组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('铣削', '铣削组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('磨削', '磨削组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('逆向工程快速原型', '逆向组', 'conn', '0.1')",
                "INSERT INTO `etc2`.`proced` (`pro_name`, `t_group_id`, `batch_name`, `weight`) VALUES ('热处理', '热处理组', 'conn', '0.1')"};

        for (String sql : sqls) {
            jdbcTemplate.execute(sql);
        }
    }

    public static void main(String[] args) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://134.175.152.210:3306/etc2?characterEncoding=utf-8&useSSL=false");
        dataSource.setUsername("scjn");
        dataSource.setPassword("123456");
        jdbcTemplate = new JdbcTemplate(dataSource);
        insertStudent(1,34);



    }


}

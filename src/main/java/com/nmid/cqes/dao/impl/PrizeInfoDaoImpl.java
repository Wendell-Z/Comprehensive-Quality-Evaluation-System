package com.nmid.cqes.dao.impl;

import com.nmid.cqes.dao.PrizeInfoDao;
import com.nmid.cqes.domain.FormatData;
import com.nmid.cqes.domain.PrizeInfo;
import com.nmid.cqes.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.TreeSet;

@Repository
public class PrizeInfoDaoImpl implements PrizeInfoDao {

    private final static String PRIZE_INFOS_SQL = " SELECT * FROM prizeinfo WHERE stuId = ? ";
    private final static String INSERT_PRIZEINFO_SQL = " INSERT INTO prizeinfo (stuId, prizeName, " +
            " materialUrl ,typeCode, score, `status`) VALUES (?,?,?,?,?,?) ";
    private final static String STATUS_SQL = "UPDATE prizeinfo SET `status` = ? WHERE id = ?";
    private final static String UPDATE_PRIZEINFO_SQL = " UPDATE prizeinfo SET prizeName = ?," +
            " materialUrl = ?, typeCode = ? , score = ?, status = ? " +
            " WHERE id = ?  ";
    private final static String DELETE_PRIZE_SQL = " DELETE FROM prizeinfo WHERE id = ? ";
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FormatData formatData;
    @Autowired
    private FileUtil fileUtil;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 返回指定学生的所有奖项信息，按照奖项类别和级别进行了排序
     *
     * @param stuId
     * @return
     */
    @Override
    public TreeSet<PrizeInfo> prizeinfos(int stuId) throws RuntimeException {
        final TreeSet<PrizeInfo> prizeInfos = new TreeSet<PrizeInfo>();
        jdbcTemplate.query(PRIZE_INFOS_SQL, new Object[]{stuId}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                PrizeInfo prizeInfo = new PrizeInfo();
                prizeInfo.setPrizeId(rs.getInt("id"));
                prizeInfo.setStuId(rs.getInt("stuId"));
                prizeInfo.setPrizeName(rs.getString("prizeName"));
                prizeInfo.setScore(rs.getByte("score"));
                prizeInfo.setMaterialUrl(rs.getString("materialUrl"));
                prizeInfo.setTypeCode(rs.getInt("typeCode"));
                prizeInfo.setStatus(rs.getByte("status"));
                prizeInfo.setLastUpdate(rs.getTimestamp("lastUpdate"));
                prizeInfos.add(prizeInfo);
            }
        });
        return prizeInfos;

    }

    /**
     * 新增一条奖项信息
     *
     * @param prizeInfo
     * @return
     */
    @Override
    public FormatData addPrizeInfo(PrizeInfo prizeInfo, MultipartFile multipartFile, String targetDirectory) {
        try {
            //用简化update(sql,params,new int[]) 总是报错 why?
            formatData = fileUtil.uploadFile(multipartFile, prizeInfo.getStuId(), prizeInfo.getPrizeName(), targetDirectory);
            if (formatData.getStatus().equals("200")) {
                jdbcTemplate.update(INSERT_PRIZEINFO_SQL, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setInt(1, prizeInfo.getStuId());
                        ps.setString(2, prizeInfo.getPrizeName());
                        ps.setString(3, (String) formatData.getData());
                        ps.setInt(4, prizeInfo.getTypeCode());
                        ps.setByte(5, prizeInfo.getScore());
                        ps.setByte(6, prizeInfo.getStatus());
                    }
                });
                formatData.set200("添加奖项信息成功！");
                return formatData;
            } else {
                return formatData;
            }

        } catch (Exception e) {
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }
    }

    /**
     * 更新奖项审核状态，数据库触发器触动了总分数更新，不能保证触发语句原子性
     * 用这个试试 PreparedStatementSetter
     *
     * @param ID
     * @param status
     * @return
     */
    @Override
    public FormatData updateStatus(int ID, byte status) {

        Object[] params = new Object[]{status, ID};
        try {
            jdbcTemplate.update(STATUS_SQL, params, new int[]{Types.TINYINT, Types.INTEGER});
            formatData.set200("修改审核状态成功！");
            return formatData;
        } catch (Exception e) {
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }

    }

    /**
     * 更新奖项信息
     *
     * @param prizeInfo
     * @return
     */
    @Override
    public FormatData updatePrizeInfo(PrizeInfo prizeInfo, MultipartFile multipartFile, String targetDirectory) {


        try {
            formatData = fileUtil.uploadFile(multipartFile, prizeInfo.getStuId(), prizeInfo.getPrizeName(), targetDirectory);
            if (formatData.getStatus().equals("200")) {

                Object[] params = new Object[]{prizeInfo.getPrizeName(), (String) formatData.getData(), prizeInfo.getTypeCode(),
                        prizeInfo.getScore(), prizeInfo.getStatus(), prizeInfo.getPrizeId()};

                jdbcTemplate.update(UPDATE_PRIZEINFO_SQL, params, new int[]{Types.VARCHAR,
                        Types.VARCHAR, Types.INTEGER, Types.TINYINT, Types.TINYINT, Types.INTEGER});

                formatData.set200("修改奖项信息成功！");
                return formatData;
            } else {
                return formatData;
            }
        } catch (Exception e) {
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }
    }

    /**
     * 删除一条奖项，前端实现逻辑:只有未审核状态的可以删除
     *
     * @param id
     * @return
     */
    @Override
    public FormatData deletePrizeInfo(int id) {

        //Object params = new Object[]{id};
        try {
            jdbcTemplate.update(DELETE_PRIZE_SQL, id);
            formatData.set200("删除成功！");
            return formatData;
        } catch (Exception e) {
            e.printStackTrace();
            formatData.set500();
            return formatData;
        }
    }

}

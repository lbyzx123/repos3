import com.mingyun.RunBoot;
import com.mingyun.sharding.entity.BOrder;
import com.mingyun.sharding.entity.Position;
import com.mingyun.sharding.entity.PositionDetail;
import com.mingyun.sharding.repository.BOrderRepository;
import com.mingyun.sharding.repository.PositionDetailRepository;
import com.mingyun.sharding.repository.PositionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 * @author luhao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunBoot.class)
public class TestShardingDatabase {

    @Resource
    private PositionRepository positionRepository;

    @Resource
    private PositionDetailRepository positionDetailRepository;

    @Resource
    private BOrderRepository orderRepository;

    @Test
    public void testAdd(){
        for (int i = 1; i <= 100; i++) {
            Position position = new Position();
//            position.setId(i);
            position.setName("lagou" + i);
            position.setSalary("10000" + i);
            position.setCity("hangzhou");
            positionRepository.save(position);
        }
    }

    @Test
    public void testAdd2(){
        for (int i = 1; i <= 100; i++) {
            Position position = new Position();
            position.setName("lagou" + i);
            position.setSalary("10000" + i);
            position.setCity("hangzhou");
            positionRepository.save(position);

            PositionDetail positionDetail = new PositionDetail();
            positionDetail.setPid(position.getId());
            positionDetail.setDescription("This is a message" + i);
            positionDetailRepository.save(positionDetail);
        }
    }

    @Test
    public void testLoad() {
       Object object = positionRepository.findPositionById(496454567416823809L);
        Object[] position = (Object[])object;
        System.out.println(position[0] + " " + position[1] + " " +position[2] + " " + position[3] + " " + position[4]);
    }


    @Repeat(100)
    @Test
    public void testShardingBOrder() {
        Random random = new Random();
        int companyId = random.nextInt(10);
        BOrder bOrder = new BOrder();
        bOrder.setDel(false);
        bOrder.setCompanyId(companyId);
        bOrder.setPositionId(496454541);
        bOrder.setUserId(223);
        bOrder.setPublishUserId(123);
        bOrder.setResumeType(1);
        bOrder.setStatus("AUTO");
        bOrder.setCreateTime(new Date());
        bOrder.setOperateTime(new Date());
        bOrder.setWorkYear("2");
        bOrder.setName("lagou");
        bOrder.setPositionName("Java");
        bOrder.setResumeId(123);
        orderRepository.save(bOrder);
    }

}
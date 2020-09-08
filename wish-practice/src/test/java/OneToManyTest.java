import com.doraemon.test.BaseTest;
import com.doraemon.wish.practice.dao.model.PracticeContractInfo;
import com.doraemon.wish.practice.dao.model.PracticeUser;
import com.doraemon.wish.practice.dao.repository.PracticeContractInfoRepository;
import com.doraemon.wish.practice.dao.repository.PracticeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OneToManyTest extends BaseTest {

    @Autowired
    private PracticeUserRepository userRepository;
    @Autowired
    private PracticeContractInfoRepository contractInfoRepository;

    @BeforeEach
    public void setUp() {
        contractInfoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void test() {
        // 新增User
        PracticeUser user = new PracticeUser();
        user.setName("小明" + System.currentTimeMillis());
        user = userRepository.save(user);

        // 新增ContractInfo
        PracticeContractInfo info = new PracticeContractInfo();
        info.setPhoneNumber("13405879876");
        user.getContractInfos().add(info);
        user = userRepository.save(user);

        // 更新ContractInfo
        Optional<PracticeUser> optional = userRepository.findById(user.getId());
        user = optional.get();
        user.getContractInfos().get(0).setPhoneNumber("18650057435");
        user = userRepository.save(user);

        user.setName("小鹿");
        userRepository.save(user);
        info = new PracticeContractInfo();
        info.setPhoneNumber("13405917899");
        user.getContractInfos().add(info);
        userRepository.save(user);

        // 删除多方
        user = userRepository.findById(user.getId()).get();
        // 1. 解除一方持有的多方对象关系
        PracticeContractInfo contractInfo = user.getContractInfos().get(1);
        user.getContractInfos().remove(contractInfo);
        user = userRepository.save(user);
        // 2. 删除对方对象
        contractInfoRepository.deleteById(contractInfo.getId());

        // 删除一方和多方
        user = userRepository.findById(user.getId()).get();
        List<PracticeContractInfo> infoList = new ArrayList<>(user.getContractInfos());
        // 1. 一方解除和多方的关系
        user.getContractInfos().clear();
        user = userRepository.save(user);
        // 2. 删除一方
        userRepository.delete(user);
        // 3. 删除多方
        for(PracticeContractInfo item : infoList) {
            contractInfoRepository.deleteById(item.getId());
        }
    }
}

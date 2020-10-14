package com.yangql.viewer4doc.application;

import com.yangql.viewer4doc.domain.Share;
import com.yangql.viewer4doc.domain.ShareRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ShareService {
    private static ShareRepository shareRepository;
    public ShareService(ShareRepository shareRepository){
        this.shareRepository = shareRepository;
    }
    public Share addShare(Share share){
        return shareRepository.save(share);
    }
    public void updateShare(Share share){
        shareRepository.updateShare(share.getUserId(),share.getFileId(),share.getLevel());
        return;
    }

    public List<Share> getShareListByFileId(Long fileId) {
        return shareRepository.findAllByFileId(fileId);
    }

    public void deleteAllShares(Long fileId) {
        shareRepository.deleteAllByFileId(fileId);
    }
}

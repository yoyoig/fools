package com.yoyoig.fools.index.service.impl;

import com.yoyoig.fools.file.IndexFileUtil;
import com.yoyoig.fools.index.domain.TermOffset;
import com.yoyoig.fools.index.domain.TmpIndex;
import com.yoyoig.fools.index.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * description
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-07-24 9:51 上午
 */
@Service
@Slf4j
public class IndexServiceImpl implements IndexService {

    @Override
    public void generateIndex() {
        log.info(">>>>>>>>>>>>>>> start generate index <<<<<<<<<<<<<<<<<");
        List<TmpIndex> tmpIndices = IndexFileUtil.readTmpIndex();
        tmpIndices.sort(Comparator.comparing(TmpIndex::getWordId));
        Map<Long, List<TmpIndex>> indexMap = tmpIndices.stream().collect(Collectors.groupingBy(TmpIndex::getWordId));
        List<TermOffset> termOffsets = IndexFileUtil.writeIndex(indexMap);
        IndexFileUtil.writeTermOffsets(termOffsets);
        log.info(">>>>>>>>>>>>>>> end generate index <<<<<<<<<<<<<<<<<");
    }
}

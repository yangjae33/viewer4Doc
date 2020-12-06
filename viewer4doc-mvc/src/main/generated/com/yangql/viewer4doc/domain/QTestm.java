package com.yangql.viewer4doc.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTestm is a Querydsl query type for Testm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTestm extends EntityPathBase<Testm> {

    private static final long serialVersionUID = 416966756L;

    public static final QTestm testm = new QTestm("testm");

    public final StringPath address = createString("address");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QTestm(String variable) {
        super(Testm.class, forVariable(variable));
    }

    public QTestm(Path<? extends Testm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestm(PathMetadata metadata) {
        super(Testm.class, metadata);
    }

}


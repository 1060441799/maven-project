package com.newframe.core.pojo.basepojo;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SortableAndManageableEntity extends SortableEntity
		implements SortableEntityIfc, IdEntityIfc, StatusEntityIfc, ManageableEntityIfc {

}

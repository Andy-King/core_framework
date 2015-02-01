package org.jee.framework.core.dao.orm.id.generator;

import java.io.Serializable;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;
import org.jee.framework.core.utils.ExceptionUtils;
import org.jee.framework.core.utils.SpringUtils;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * <pre>
 * id生成策略，到指定的datasource里读取指定的sequence，生成ID
   {@code
    @Id
	//@SequenceGenerator(name = "SEQ_P_USER_ID", sequenceName = "SEQ_P_USER_ID", allocationSize = 1)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_P_USER_ID")
	@GeneratedValue(generator = "BeanIdGenerator")  
	@GenericGenerator(name = "BeanIdGenerator", strategy = "org.jee.framework.core.dao.orm.id.generator.BeanIdGenerator",   
		parameters = {@Parameter(name = "datasourceName", value = "webDataSource"), 
	@Parameter(name = "sequenceName", value = "SEQ_P_USER_ID") })
	public Long getId() {
		return this.id;
	}
   
   }

 * </pre>
 */
public class BeanIdGenerator implements IdentifierGenerator,Configurable  {
	
	private String sequenceName ; //the sequence name
	
	private String datasourceName ;//the datasource

	private DataSource datasource;
	
	private JdbcTemplate jdbcTemplate;
	
	{
		datasource =  SpringUtils.getBean(datasourceName);
		
		if(null == datasource){
			ExceptionUtils.unchecked("Not found datasource:" + datasourceName);
		}
		
		jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	public Serializable generate(SessionImplementor session, Object obj) 
		throws HibernateException{
		
		StringBuilder sb = new StringBuilder();
			sb.append("SELECT ");
			sb.append(sequenceName);
			sb.append(".nextVal FROM dual");
			
		final Number number = jdbcTemplate.queryForObject(sb.toString(), Long.class);
		return (number != null ? number.longValue() : 0);
	}

	@Override
	public void configure(Type arg0, Properties params, Dialect dialect)
			throws MappingException {
		datasourceName = params.getProperty("datasourceName");
		sequenceName = params.getProperty("sequenceName");
	}

}
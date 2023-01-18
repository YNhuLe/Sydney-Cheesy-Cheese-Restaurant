package ca.sheridancollege.nhuyle.a4.database;

import java.util.ArrayList;
/**Name: NhuY Le
 * Assignment: Sydney's Cheesey Cheese 2.2
Date: November 25, 2022

Description: This application allow clients visit the Sydney's Cheesey Cheese 
website,view cheese and information about us.But only authenticated users with 
 correct name and password can log in and see the adding cheese to the inventory
calculate the value, and display cheese's information to the table.  */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.nhuyle.a4.beans.Cheese;
import ca.sheridancollege.nhuyle.a4.beans.Units;
import ca.sheridancollege.nhuyle.a4.beans.User;

@Repository
public class DatabaseAccess {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	private BCryptPasswordEncoder encoder;

	// Return List of Unit objects for each record in the "units" table
	public List<Units> getUnits() {
		String sql = "SELECT *  FROM units;";
		List<Units> unitsList = jdbc.query(sql,
				new BeanPropertyRowMapper<Units>(Units.class));
		return unitsList;
	}

	/*
	 * returns Map of Units, and display the units name in places where cheese are
	 * listing
	 */
	public Map<String, Units> getUnitMap() {
		List<Units> unitList = getUnits();
		Map<String, Units> unitMap = new HashMap<String, Units>();
		for (Units u : unitList) {
			unitMap.put(u.getDescription(), u);
		}
		return unitMap;
	}

	/*
	 * return a list of Cheese object for each record in the cheese table, sorted by
	 * cheese name and unit id
	 */
	public List<Cheese> getCheeseInventory() {
		String sql = "SELECT * FROM cheeses ORDER BY name, unitsId;";
		List<Cheese> cheeseList = jdbc.query(sql, 
				new BeanPropertyRowMapper<Cheese>(Cheese.class));
		return cheeseList;
	}

	/* accepts a Cheese object and insert it into the cheese table */
	public int insertCheese(Cheese newCheese) {
		String sql = "INSERT INTO cheeses(name, quantity, weight," + "unitsId, price, specSheet)"
				+ "VALUES(:name,:quantity,:weight," + ":unitsId,:price,:specSheet);";

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("name", newCheese.getName())
		.addValue("quantity", newCheese.getQuantity())
				.addValue("weight", newCheese.getWeight())
				.addValue("unitsId", newCheese.getUnitsId())
				.addValue("price", newCheese.getPrice())
				.addValue("specSheet", newCheese.getSpecSheet());
		return jdbc.update(sql, params);
	}

	/*
	 * returns a list of Cheese object for distinct records in the cheese table
	 */
	public List<Cheese> getCheese() {
		String sql = "SELECT distinct name ,unitsId  FROM cheeses;";
		List<Cheese> newCheeses = jdbc.query(sql,
				new BeanPropertyRowMapper<Cheese>(Cheese.class));
		return newCheeses;
	}

	// find user account
	public User findUesrAccount(String userName) {
		String sql = "SELECT * FROM users WHERE userName=:userName;";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userName", userName);

		List<User> list = jdbc.query(sql, params, 
				new BeanPropertyRowMapper<User>(User.class));

		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// retrieve the uer-role for a specific userid
	public List<String> getRolesByUserId(long userId) {
		String sql = "SELECT user_role.userId, roles.rolename FROM user_role, roles "
			+ "WHERE user_role.roleId = roles.roleId AND user_role.userId=:user;";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("user", userId);

		List<String> roles = new ArrayList<String>();

		List<Map<String, Object>> rows = jdbc.queryForList(sql, params);

		for (Map<String, Object> row : rows) {
			roles.add((String) row.get("roleName"));
		}
		return roles;
	}

	//add new user to the database	
	public long addUser(String userName, String email, String password) {
		String sql = "INSERT INTO users(email, userName, password, enabled)" + 
	"VALUES (:email, :name, :pass , true);";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", email)
		.addValue("name", userName)
		.addValue("pass", encoder.encode(password));

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(sql, params, keyHolder);
		return (long) keyHolder.getKey();
	}

	//add role of new user to the database
	public int addUserRole( long userId, long roleId) {
		String sql="INSERT INTO user_role(userId,roleId)"
				+ "VALUES (:user, :role);";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("user", userId)
		.addValue("role", roleId);		
		return jdbc.update(sql, params);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
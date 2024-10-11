package com.veracode.verademo.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import java.net.URLEncoder;
import java.util.*;

public class RemoveAccountCommand implements BlabberCommand {
	private static final Logger logger = LogManager.getLogger("VeraDemo:RemoveAccountCommand");

	private Connection connect;

	public RemoveAccountCommand(Connection connect, String username) {
		super();
		this.connect = connect;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.veracode.verademo.commands.Command#execute()
	 */
	@Override
	public void execute(String blabberUsername) {
		String sqlQuery = StringUtils.normalizeSpace("DELETE FROM listeners WHERE blabber=? OR listener=?;");
		logger.info(sqlQuery);
		PreparedStatement action;
		try {
			action = connect.prepareStatement(sqlQuery);

			action.setString(1, blabberUsername);
			action.setString(2, blabberUsername);
			action.execute();

			sqlQuery = "SELECT blab_name FROM users WHERE username = '" + blabberUsername + "'";
			Statement sqlStatement = connect.createStatement();
			logger.info(sqlQuery);
			ResultSet result = sqlStatement.executeQuery(sqlQuery);
			result.next();

			/* START EXAMPLE VULNERABILITY */
			Set<String> whitelistResultGetstring1 = new HashSet<>(Arrays.asList("item1", "item2", "item3"));
			if (!result.getString(1).matches("\\w+(\\s*\\.\\s*\\w+)*") && !whitelistResultGetstring1.contains(result.getString(1)))
			    throw new IllegalArgumentException();
			String event = "Removed account for blabber " + URLEncoder.encode(result.getString(1).toString());
			sqlQuery = "INSERT INTO users_history (blabber, event) VALUES ('" + blabberUsername + "', '" + event + "')";
			logger.info(sqlQuery);
			sqlStatement.execute(sqlQuery);

			sqlQuery = "DELETE FROM users WHERE username = '" + blabberUsername + "'";
			logger.info(sqlQuery);
			sqlStatement.execute(sqlQuery);
			sqlStatement2.execute(sqlQuery);
			/* END EXAMPLE VULNERABILITY */

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

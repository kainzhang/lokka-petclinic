package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Pet;
import util.Connect;

public class PetDAO {
	public boolean insertPet(Pet pet) {
		String stmt = 
				" BEGIN;" +
                " INSERT INTO pet" +
                " VALUES" +
                " (null, '"+pet.getName()+
                "','"+pet.getBday()+
                "',"+pet.getSpeciesId()+
                ","+pet.getOwnerId()+
                ",'"+pet.getPic()+"');" +
                " COMMIT;";
		try {
			Connect.exeUpdate(stmt);
		} catch (SQLException e) {
            System.out.print("Error occurred while INSERT Operation: " + e);
            return false;
		}
		return true;
	}
	
	public boolean deletePet(Integer id) {
		String stmt = 
				" BEGIN;" +
                " DELETE FROM pet" +
                " WHERE id="+id+";" +
                " COMMIT;";
        try {
            Connect.exeUpdate(stmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while DELETE Operation: " + e);
            return false;
        }
        return true;
	}
	
	public boolean updatePet(Pet pet) {
    	String stmt =
				" BEGIN;" +
                " UPDATE pet" +
                " SET name = '"+pet.getName()+"' ," +
                " BDAY = '"+pet.getBday()+"' ," +
                " pic = '"+pet.getPic()+"'," +
                " speciesid = "+pet.getSpeciesId()+"," +
                " ownerid = "+pet.getOwnerId()+" " +
                " WHERE id = '"+pet.getId()+"'; " +
                " COMMIT;";
		try {
		    Connect.exeUpdate(stmt);
		} catch (SQLException e) {
		    System.out.print("Error occurred while UPDATE Operation: " + e);
		    return false;
		}
		return true;
	}
	
	private List<Pet> getList(String stmt) {
		ResultSet rs;
		List<Pet> list = new ArrayList<Pet>();
		try {
			rs = Connect.exeQuery(stmt);
			while(rs.next()) {
				Pet pet = new Pet();
				pet.setId(rs.getInt("ID"));
				pet.setName(rs.getString("NAME"));
				pet.setBday(rs.getString("BDAY"));
				pet.setPic(rs.getString("PIC"));
				pet.setOwnerId(rs.getInt("OWNERID"));
				pet.setOwner(rs.getString("OWNER"));
				pet.setSpeciesId(rs.getInt("SPECIESID"));
				pet.setSpecies(rs.getString("SPECIES"));
				list.add(pet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;	
	}
	
	
	public List<Pet> searchPets(String keyword) {
		String stmt = " SELECT * FROM pet WHERE name LIKE '%"+keyword+"%'; ";
		return getList(stmt);
	}
	
	public List<Pet> getPets() {
		String stmt = " SELECT * FROM pet; ";
		return getList(stmt);
	}
}
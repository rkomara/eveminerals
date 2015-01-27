/*
 * EveMinerals
 * Copyright (C) 2014  Rastislav Komara
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package komara.eo.remote;

import com.beimin.eveapi.account.characters.EveCharacter;
import com.beimin.eveapi.core.ApiAuth;
import com.beimin.eveapi.shared.locations.ApiLocation;
import junit.framework.Assert;
import komara.eo.mineral.Mineral;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Rastislav Komara on 2/8/14.
 */
public class RemoteAPIServiceImplTest {

    private Integer keyID;
    private String vCode;
    private Long characterID;

    @Before
    public void setUp() {
        String keyIdString = System.getProperty("remote.keyID", null);
        if (keyIdString != null) {
            keyID = Integer.parseInt(keyIdString);
        }
        Assert.assertNotNull("Key ID not specified. Please set up keyID using -Dremote.keyID on command line", keyID);

        vCode = System.getProperty("remote.vCode", null);
        Assert.assertNotNull("Verification code not found. Please set verification code using -Dremote.vCode on command line", vCode);

        keyIdString = System.getProperty("remote.charID", null);
        if (keyIdString != null) {
            characterID = Long.parseLong(keyIdString);
        }
        Assert.assertNotNull("Key ID not specified. Please set up keyID using -Dremote.charID on command line", characterID);
    }

    @After
    public void tearDown() {
        keyID = null;
        vCode = null;
        characterID = null;
    }

    @Test
    public void testAuthorize() throws Exception {
        RemoteAPIServiceImpl service = new RemoteAPIServiceImpl();
        ApiAuth<?> authorize = service.authorize(keyID, vCode);
        Assert.assertNotNull(authorize);
        Assert.assertEquals(keyID.intValue(), authorize.getKeyID());
        Assert.assertEquals(vCode, authorize.getVCode());
        Assert.assertNull(authorize.getCharacterID());
    }

    @Test
    public void testGetCharacters() throws Exception {
        RemoteAPIServiceImpl service = new RemoteAPIServiceImpl();
        ApiAuth<?> authorize = service.authorize(keyID, vCode);
        Set<EveCharacter> characters = service.getCharacters(authorize);
        boolean ok = false;
        for (EveCharacter character : characters) {
            if (character.getCharacterID() == characterID) {
                ok = true;
                break;
            }
        }
        Assert.assertTrue("Character with expected id: " + characterID + " not found.", ok);
    }

    @Test
    public void testGetAvailableMineralQuantities() throws Exception {
        RemoteAPIServiceImpl service = new RemoteAPIServiceImpl();
        ApiAuth<?> authorize = service.authorize(keyID, vCode);
        authorize.setCharacterID(characterID);
        Map<Integer,Long> quantities = service.getAvailableMineralQuantities(authorize);
        for (Map.Entry<Integer, Long> entry : quantities.entrySet()) {
            System.out.printf("%s: %d%n", Mineral.find(entry.getKey().intValue()), entry.getValue());
        }
    }

    @Test
    public void testGetLocationsWithMinerals() throws Exception {
        RemoteAPIServiceImpl service = new RemoteAPIServiceImpl();
        ApiAuth<?> authorize = service.authorize(keyID, vCode);
        authorize.setCharacterID(characterID);
        Collection<ApiLocation> locations = service.getLocationsWithMinerals(authorize);
        for (ApiLocation location : locations) {
            System.out.printf("%s [%d]%n", location.getItemName(), location.getItemID());
        }
    }

    @Test
    public void testGetMineralsByLocation() throws Exception {
        RemoteAPIServiceImpl service = new RemoteAPIServiceImpl();
        ApiAuth<?> authorize = service.authorize(keyID, vCode);
        authorize.setCharacterID(characterID);
        Map<Long, long[]> mineralsByLocation = service.getMineralsByLocation(authorize);
        Mineral[] minerals = Mineral.values();
        for (Map.Entry<Long, long[]> entry : mineralsByLocation.entrySet()) {
            StringBuilder sb = new StringBuilder("Location: ").append(entry.getKey()).append("\n");
            long[] volumes = entry.getValue();
            for (int i = 0; i < volumes.length; i++) {
                sb.append("\t").append(minerals[i]).append(": ").append(volumes[i]).append("\n");
            }
            System.out.print(sb);
        }
    }

    @Test
    public void testGetAllCharacters() throws Exception {
        RemoteAPIServiceImpl service = new RemoteAPIServiceImpl();
        Set<EveCharacter> allCharacters = service.getAllCharacters(new AbstractMap.SimpleEntry<Integer, String>(keyID, vCode));
        for (EveCharacter character : allCharacters) {
            System.out.printf("%s: %s%n", character.getName(), character.getCorporationName());
        }
    }
}

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

import com.beimin.eveapi.EveApi;
import com.beimin.eveapi.account.characters.CharactersHandler;
import com.beimin.eveapi.account.characters.CharactersResponse;
import com.beimin.eveapi.account.characters.EveCharacter;
import com.beimin.eveapi.connectors.ApiConnector;
import com.beimin.eveapi.core.*;
import com.beimin.eveapi.exception.ApiException;
import com.beimin.eveapi.shared.assetlist.AssetListHandler;
import com.beimin.eveapi.shared.assetlist.AssetListResponse;
import com.beimin.eveapi.shared.assetlist.EveAsset;
import com.beimin.eveapi.shared.locations.ApiLocation;
import com.beimin.eveapi.shared.locations.LocationsHandler;
import com.beimin.eveapi.shared.locations.LocationsResponse;
import com.beimin.eveapi.utils.StringUtils;

import java.util.*;

/**
 * Created by Rastislav Komara on 2/4/14.
 */
public class RemoteAPIServiceImpl implements RemoteAPIService {

    private final EveApi api;

    public RemoteAPIServiceImpl() {
        EveApi.setConnector(new ApiConnector());
        this.api = new EveApi();
    }

    @Override
    public ApiAuth<?> authorize(int keyID, String vCode) {
        ApiAuthorization authorization = new ApiAuthorization(keyID, vCode);
        api.setAuth(authorization);
        return api.getAuth();
    }

    @Override
    public Set<EveCharacter> getCharacters(ApiAuth<?> auth) throws ApiException {
        return api.getCharacters();
    }

    @Override
    public Map<Integer, Long> getAvailableMineralQuantities(ApiAuth<?> character) throws ApiException {
        Map<Integer, Long> result = new HashMap<Integer, Long>();
        api.setAuth(character);
        AssetListResponse response = EveApi.getConnector().execute(new ApiRequest(ApiPath.CHARACTER, ApiPage.ASSET_LIST, 2, character), new AssetListHandler(), AssetListResponse.class);
        Stack<EveAsset<?>> assets = new Stack<EveAsset<?>>();
        assets.addAll(response.getAll());
        while (!assets.isEmpty()) {
            EveAsset<?> asset = assets.pop();
            if (asset.getAssets() != null) {
                assets.addAll(asset.getAssets());
            }
            if (isMineral(asset)) {
                int typeID = asset.getTypeID();
                long volume = 0;
                if (result.containsKey(typeID)) {
                    volume = result.get(typeID);
                }
                volume += asset.getQuantity();
                result.put(typeID, volume);
            }
        }

        return result;
    }

    @Override
    public Collection<ApiLocation> getLocationsWithMinerals(ApiAuth<?> character) throws ApiException {
        Set<Long> result = new TreeSet<Long>();
        api.setAuth(character);
        ApiConnector connector = EveApi.getConnector();
        AssetListResponse response = connector.execute(new ApiRequest(ApiPath.CHARACTER, ApiPage.ASSET_LIST, 2, character), new AssetListHandler(), AssetListResponse.class);
        Stack<EveAsset<?>> assets = new Stack<EveAsset<?>>();
        assets.addAll(response.getAll());
        while (!assets.isEmpty()) {
            EveAsset<?> asset = assets.pop();
            if (asset.getAssets() != null) {
                assets.addAll(asset.getAssets());
            }
            if (isMineral(asset) && asset.getLocationID() != null) {
                result.add(asset.getLocationID());
            }
        }

        Long[] objects = result.toArray(new Long[0]);
        long[] ids = new long[objects.length];
        for(int i = 0; i < ids.length; i++) {
            ids[i] = objects[i];
        }
        String join = StringUtils.join(",", ids);
        Map<String, String> params = Collections.singletonMap("IDs", join);
        LocationsResponse locationsResponse = connector.execute(new ApiRequest(ApiPath.CHARACTER, ApiPage.LOCATIONS, 2, character, params),
                new LocationsHandler(), LocationsResponse.class);
        return locationsResponse.getAll();
    }

    @Override
    public Map<Long, long[]> getMineralsByLocation(ApiAuth<?> character) throws ApiException {
        Map<Long, long[]> result = new HashMap<Long, long[]>();
        api.setAuth(character);
        ApiConnector connector = EveApi.getConnector();
        AssetListResponse response = connector.execute(new ApiRequest(ApiPath.CHARACTER, ApiPage.ASSET_LIST, 2, character), new AssetListHandler(), AssetListResponse.class);
        Stack<EveAsset<?>> assets = new Stack<EveAsset<?>>();
        assets.addAll(response.getAll());
        while (!assets.isEmpty()) {
            EveAsset<?> asset = assets.pop();
            if (asset.getAssets() != null) {
                assets.addAll(asset.getAssets());
            }
            Long locationID = asset.getLocationID();
            if (isMineral(asset) && locationID != null) {
                long[] materials = result.get(locationID);
                if (materials == null) {
                    materials = new long[8];
                    Arrays.fill(materials, 0);
                    result.put(locationID, materials);
                }
                int index = typeToIndex(asset.getTypeID());
                materials[index] = materials[index] + asset.getQuantity();
            }
        }
        return result;
    }

    private boolean isMineral(EveAsset<?> asset) {
        int typeID = asset.getTypeID();
        /*
            34          Tritanium
            35          Pyerite
            36          Mexallon
            37          Isogen
            38          Nocxium
            39          Zydrine
            40          Megacyte
            11399       Morphite
         */
        return (typeID == 11399 || (typeID <= 40 && typeID >= 34));
    }

    private int typeToIndex(int typeID) {
        if (typeID == 11399) return 7;
        if (typeID <= 40 && typeID >= 34) {
            return typeID - 34;
        }
        return -1;
    }

    @Override
    public Set<EveCharacter> getAllCharacters(Map.Entry<Integer, String>... keys) throws ApiException {
        ApiConnector connector = EveApi.getConnector();
        Map<String, String> params = new HashMap<String, String>();
        Set<EveCharacter> eveCharacters = new HashSet<EveCharacter>();
        for (Map.Entry<Integer, String> key : keys) {
            params.put("keyID", key.getKey().toString());
            params.put("vCode", key.getValue());
            CharactersResponse response = connector.execute(new ApiRequest(ApiPath.ACCOUNT, ApiPage.CHARACTERS, 2, params),
                    new CharactersHandler(), CharactersResponse.class);
            eveCharacters.addAll(response.getAll());
        }
        return eveCharacters;
    }
}

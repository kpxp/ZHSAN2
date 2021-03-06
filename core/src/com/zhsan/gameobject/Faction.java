package com.zhsan.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.opencsv.CSVReader;
import com.zhsan.common.Utility;
import com.zhsan.common.exception.EmptyFileException;
import com.zhsan.common.exception.FileReadException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14/3/2015.
 */
public class Faction extends GameObject {

    private String name;

    private Faction(int id) {
        super(id);
    }

    public static final GameObjectList<Faction> fromCSVQuick(String path, int version) {
        GameObjectList<Faction> result = new GameObjectList<>();

        FileHandle f = Gdx.files.external(path + File.separator + "Faction.csv");
        try (CSVReader reader = new CSVReader(new InputStreamReader(f.read()))) {
            String[] line;
            int index = 0;
            while ((line = reader.readNext()) != null) {
                index++;
                if (index == 1) continue; // skip first line.

                Faction t = new Faction(Integer.parseInt(line[0]));
                t.name = line[3];
                result.add(t);
            }

            return result;
        } catch (IOException e) {
            throw new FileReadException(path + File.separator + "Faction.csv", e);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

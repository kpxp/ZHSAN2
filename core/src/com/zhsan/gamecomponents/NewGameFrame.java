package com.zhsan.gamecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.zhsan.common.Utility;
import com.zhsan.common.exception.FileReadException;
import com.zhsan.gamecomponents.common.TextWidget;
import com.zhsan.gameobject.GameScenario;
import com.zhsan.gameobject.GameSurvey;
import com.zhsan.resources.GlobalStrings;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

/**
 * Created by Peter on 8/3/2015.
 */
public class NewGameFrame extends GameFrame {

    public static final String RES_PATH = GameFrame.RES_PATH + "New" + File.separator;

    private int margins;
    private int listPaddings;
    private Color listSelectedColor;

    private ScrollPane scenarioPane, scenarioDescriptionPane, factionPane;
    private TextWidget.Setting scenarioElement, scenarioDescriptionStyle, factionStyle;

    private void loadXml() {
        FileHandle f = Gdx.files.external(RES_PATH + "NewGameFrameData.xml");
        String dataPath = RES_PATH + File.separator + "Data" + File.separator;

        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(f.read());

            margins = Integer.parseInt(dom.getElementsByTagName("Margins").item(0).getAttributes()
                    .getNamedItem("value").getNodeValue());
            listPaddings = Integer.parseInt(dom.getElementsByTagName("Lists").item(0).getAttributes()
                    .getNamedItem("padding").getNodeValue());
            listSelectedColor = Utility.readColorFromXml(
                    Integer.parseUnsignedInt(dom.getElementsByTagName("Lists").item(0).getAttributes()
                    .getNamedItem("selectedColor").getNodeValue())
            );
            scenarioElement = TextWidget.Setting.fromXml(dom.getElementsByTagName("ScenarioList").item(0));
        } catch (Exception e) {
            throw new FileReadException(RES_PATH + "NewGameFrameData.xml", e);
        }
    }

    public NewGameFrame(float width, float height) {
        super(width, height, GlobalStrings.getString(GlobalStrings.NEW_GAME), new GameFrame.OnClick() {
            @Override
            public void onOkClicked() {

            }

            @Override
            public void onCancelClicked() {

            }
        });

        loadXml();

        float scenarioPaneHeight = (getTopBound() - getBottomBound() - margins * 3) / 2;
        float scenarioPaneWidth = (getRightBound() - getLeftBound() - margins * 3) / 2;

        List<GameSurvey> surveys = GameScenario.loadAllGameSurveys();

        Table scenarioList = new Table();
        for (GameSurvey i : surveys) {
            TextWidget widget = new TextWidget(scenarioElement, i.title);
            widget.setTouchable(Touchable.enabled);
            scenarioList.add(widget).size(scenarioPaneWidth, widget.computeNeededHeight(scenarioPaneWidth) + listPaddings);
            scenarioList.row();
        }
        scenarioList.setX(getLeftBound());
        scenarioList.setY(getTopBound() - scenarioPaneHeight);
        scenarioList.setWidth(scenarioPaneWidth);
        scenarioList.setHeight(scenarioPaneHeight);
        scenarioList.top().left();
        // scenarioList.debugAll();

//        scenarioPane = new ScrollPane(scenarioList);
//        scenarioPane.setX(margins);
//        scenarioPane.setY(getHeight() - margins - scenarioPaneHeight);
//        scenarioPane.setWidth(scenarioPaneWidth);
//        scenarioPane.setHeight(scenarioPaneHeight);

        addActor(scenarioList);
    }

}

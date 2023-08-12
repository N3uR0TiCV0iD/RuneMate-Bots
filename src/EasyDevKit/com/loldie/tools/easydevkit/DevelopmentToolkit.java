package com.loldie.tools.easydevkit;

import com.runemate.bots.dev.ui.DevelopmentToolkitPage;
import com.runemate.bots.dev.ui.QueriableTreeItem;
import com.runemate.bots.dev.ui.ReflectiveTreeItem;
import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.GameEvents;
import com.runemate.game.api.hybrid.RuneScape;
import com.runemate.game.api.hybrid.cache.configs.EnumDefinitions;
import com.runemate.game.api.hybrid.cache.configs.OverlayDefinitions;
import com.runemate.game.api.hybrid.cache.configs.SpotAnimationDefinitions;
import com.runemate.game.api.hybrid.cache.configs.UnderlayDefinitions;
import com.runemate.game.api.hybrid.cache.materials.Materials;
import com.runemate.game.api.hybrid.entities.Actor;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.entities.definitions.NpcDefinition;
import com.runemate.game.api.hybrid.entities.details.Interactable;
import com.runemate.game.api.hybrid.entities.details.Rotatable;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.*;
import com.runemate.game.api.hybrid.local.hud.GraphicsConfiguration;
import com.runemate.game.api.hybrid.local.hud.HintArrows;
import com.runemate.game.api.hybrid.local.hud.Menu;
import com.runemate.game.api.hybrid.local.hud.Model;
import com.runemate.game.api.hybrid.local.hud.interfaces.*;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.net.GrandExchange;
import com.runemate.game.api.hybrid.region.*;
import com.runemate.game.api.hybrid.util.Validatable;
import com.runemate.game.api.osrs.local.hud.interfaces.*;
import com.runemate.game.api.rs3.local.CombatMode;
import com.runemate.game.api.rs3.local.InterfaceMode;
import com.runemate.game.api.rs3.local.hud.Powers;
import com.runemate.game.api.rs3.local.hud.interfaces.*;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionWindow;
import com.runemate.game.api.rs3.local.hud.interfaces.legacy.LegacyTab;
import com.runemate.game.api.rs3.region.Familiars;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.LoopingBot;
import com.runemate.game.api.script.framework.listeners.*;
import com.runemate.game.api.script.framework.listeners.events.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;
import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
/*
 *  /!\ /!\ /!\ THIS FILE IS JUST TO BE USED AS A REFERENCE /!\ /!\ /!\
 *  					IGNORE FROM BUILD PATH!!!
 *  
 */
/**
 * Created by Ivan on 1/20/2016.
 */
public class DevelopmentToolkit extends LoopingBot implements EmbeddableUI,
        GrandExchangeListener, ChatboxListener, InventoryListener, MoneyPouchListener, SkillListener, VarpListener, VarbitListener {
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    static {
        ReflectiveTreeItem.BLACKLISTED_METHODS.clear();
        ReflectiveTreeItem.BLACKLISTED_METHODS.addAll(Arrays.asList(
                new Pair<>(Object.class, "getClass"),
                new Pair<>(Object.class, "hashCode"),
                new Pair<>(Enum.class, "getDeclaringClass"),
                new Pair<>(Skill.class, "getExperienceToNextLevel"),
                new Pair<>(Skill.class, "getExperienceAsPercent"),
                new Pair<>(Skill.class, "getExperienceToNextLevelAsPercent"),
                new Pair<>(LocatableEntity.class, "getHighPrecisionPosition"),
                new Pair<>(Interactable.class, "getInteractionPoint"),
                new Pair<>(Interactable.class, "getVisibility"),
                new Pair<>(Validatable.class, "isValid"),
                new Pair<>(Rotatable.class, "getHighPrecisionOrientation"),
                new Pair<>(Model.class, "getBoundingModel"),
                new Pair<>(Coordinate.class, "getPosition"),
                new Pair<>(Coordinate.class, "getCollisionFlag"),
                new Pair<>(Coordinate.class, "getHighPrecisionPosition"),
                new Pair<>(Coordinate.class, "isReachable"),
                new Pair<>(Coordinate.class, "getReachableCoordinates"),
                new Pair<>(Coordinate.class, "getBounds"),
                new Pair<>(Coordinate.class, "isLoaded"),
                new Pair<>(Area.class, "getRandomCoordinate"),
                new Pair<>(Area.Rectangular.class, "getArea"),
                new Pair<>(LocatableEntity.class, "getPlane"),
                new Pair<>(Actor.class, "getHeight"),
                new Pair<>(GameObject.class, "getCollisionType"),
                new Pair<>(GameObject.class, "getOrientation"),
                new Pair<>(Player.class, "getFamiliar"),
                new Pair<>(NpcDefinition.class, "getModelOffsets"),
                new Pair<>(ItemDefinition.class, "getInventoryActionArray"),
                new Pair<>(ItemDefinition.class, "getGroundActionArray"),
                new Pair<>(GameObjectDefinition.class, "getRawAppearance"),
                new Pair<>(GameObjectDefinition.class, "getModelTypes"),
                new Pair<>(GameObjectDefinition.class, "getModelXScale"),
                new Pair<>(GameObjectDefinition.class, "getModelYScale"),
                new Pair<>(GameObjectDefinition.class, "getModelZScale"),
                new Pair<>(GameObjectDefinition.class, "getModelHeight"),
                new Pair<>(InterfaceContainer.class, "getId"),
                new Pair<>(InterfaceComponent.class, "getLayerId"),
                new Pair<>(InterfaceComponent.class, "getId"),
                new Pair<>(InterfaceComponent.class, "getContainer"),
                new Pair<>(InterfaceComponent.class, "getParentComponent"),
                new Pair<>(InterfaceComponent.class, "getContentType"),
                new Pair<>(Traversal.class, "getDefaultWeb"),
                new Pair<>(Environment.class, "isOSRS"),
                new Pair<>(Environment.class, "isRS3"),
                new Pair<>(Environment.class, "getBot"),
                new Pair<>(Mouse.class, "getPathGenerator"),
                new Pair<>(Region.class, "getCollisionFlags"),
                new Pair<>(Region.class, "getHighPrecisionBase"),
                new Pair<>(Region.class, "getCurrentPlane"),
                new Pair<>(Bank.class, "getItems")
        ));
        ReflectiveTreeItem.ALIASED_METHODS.addAll(Collections.singletonList(
                new Pair<>(GameObject.class, new Pair<>(createSpoofedMethod(GameObject.class, "getName", String.class), o -> {
                    GameObjectDefinition god = null;
                    String name = o instanceof GameObject && (god = ((GameObject) o).getDefinition()) != null ? god.getName() : null;
                    if (Objects.equals("null", name)) {
                        name = null;
                    }
                    if (name == null && god != null && (god = god.getLocalState()) != null) {
                        name = god.getName();
                    }
                    if (Objects.equals("null", name)) {
                        name = null;
                    }
                    return name;
                }))
        ));

        DevelopmentToolkitPage.OVERRIDDEN_TO_STRINGS.put(Point.class, o -> {
            Point p = (Point) o;
            return "Point(" + p.x + ", " + p.y + ')';
        });
        DevelopmentToolkitPage.OVERRIDDEN_TO_STRINGS.put(Color.class, o -> {
            Color c = (Color) o;
            int alpha = c.getAlpha();
            if (alpha != 0xFF) {
                return "Rgba(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + alpha + ')';
            } else {
                return "Rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ')';
            }
        });
        DevelopmentToolkitPage.OVERRIDDEN_TO_STRINGS.put(Dimension.class, o -> {
            Dimension d = (Dimension) o;
            return "Dimension(" + d.width + " x " + d.height + ')';
        });
        DevelopmentToolkitPage.OVERRIDDEN_TO_STRINGS.put(Rectangle.class, o -> {
            Rectangle r = (Rectangle) o;
            return "Rectangle(" + r.x + ", " + r.y + ", " + r.width + ", " + r.height + ')';
        });
        DevelopmentToolkitPage.OVERRIDDEN_TO_STRINGS.put(Rectangle2D.Double.class, o -> {
            Rectangle2D.Double rd = (Rectangle2D.Double) o;
            return "Rectangle2D(" + rd.x + ", " + rd.y + ", " + rd.width + ", " + rd.height + ')';
        });
    }

    private DevelopmentToolkitPage developmentToolkitPage;
    private TreeItem<Pair<Method, Object>> grandexchangeTreeItem, chatboxTreeItem, inventoryTreeItem, moneyPouchTreeItem, skillTreeItem, varpTreeItem, varbitTreeItem;
    private ObjectProperty<DevelopmentToolkitPage> botInterfaceProperty;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public DevelopmentToolkit() {
        setEmbeddableUI(this);
    }

    /*
           Class<?> declaringClass,
           String name,
           Class<?>[] parameterTypes,
           Class<?> returnType,
           Class<?>[] checkedExceptions,
           int modifiers,
           int slot,
           String signature,
           byte[] annotations,
           byte[] parameterAnnotations,
           byte[] annotationDefault)
     */
    public static Method createSpoofedMethod(Class<?> declaringClass, String name, Class<?> returnType) {
        try {
            Constructor<Method> methodConstructor = Method.class.getDeclaredConstructor(Class.class, String.class, Class[].class, Class.class, Class[].class, int.class, int.class, String.class, byte[].class, byte[].class, byte[].class);
            if (methodConstructor != null) {
                boolean isAccessible = methodConstructor.isAccessible();
                if (!isAccessible) {
                    methodConstructor.setAccessible(true);
                }
                Method method = methodConstructor.newInstance(declaringClass, name, EMPTY_CLASS_ARRAY, returnType, EMPTY_CLASS_ARRAY, Member.PUBLIC, -1, null, EMPTY_BYTE_ARRAY, EMPTY_BYTE_ARRAY, EMPTY_BYTE_ARRAY);
                if (!isAccessible) {
                    methodConstructor.setAccessible(false);
                }
                return method;
            }
        } catch (ReflectiveOperationException ex) {
            System.err.println("Point C Thread: " + Thread.currentThread().getName() + "  ");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onStop() {
        Platform.runLater(() -> {
            botInterfaceProperty().get().getEntitiesTreeTableView().setRoot(null);
            botInterfaceProperty().get().getEventsTreeTableView().setRoot(null);
            botInterfaceProperty().get().getMiscTreeTableView().setRoot(null);
            botInterfaceProperty().get().getDatabaseTreeTableView().setRoot(null);
            botInterfaceProperty().get().setDisable(true);
            executorService.shutdown();
        });
    }

    @Override
    public void onStart(String... arguments) {
        GameEvents.Universal.LOGIN_HANDLER.disable();
        GameEvents.Universal.LOBBY_HANDLER.disable();
        GameEvents.Universal.INTERFACE_CLOSER.disable();
        GameEvents.Universal.UNEXPECTED_ITEM_HANDLER.disable();
        while (botInterfaceProperty == null) {
            Execution.delay(100);
        }
        QueriableTreeItem.setExecutorService(executorService);
        StringProperty entitiesSearchTextProperty = botInterfaceProperty.get().getEntitiesSearchTextField().textProperty();
        BooleanProperty entitiesSearchRegexProperty = botInterfaceProperty.get().getEntitiesSearchRegexCheckBox().selectedProperty();
        botInterfaceProperty().get().getEntitiesTreeTableView().getRoot().getChildren().setAll(
                buildPseudoRootTreeItem(Players.class.getSimpleName(), () -> Players.getLoaded().sortByDistance(), entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(Npcs.class.getSimpleName(), () -> Npcs.getLoaded().sortByDistance(), entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(GameObjects.class.getSimpleName(), () -> GameObjects.getLoaded().sortByDistance(), entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(GroundItems.class.getSimpleName(), () -> GroundItems.getLoaded().sortByDistance(), entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(Projectiles.class.getSimpleName(), () -> Projectiles.getLoaded().sortByDistance(), entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(HintArrows.class.getSimpleName(), () -> HintArrows.getLoaded().sortByDistance(), entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(InterfaceContainers.class.getSimpleName(), InterfaceContainers::getLoaded, entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(InterfaceComponent.class.getSimpleName() + 's', () -> Interfaces.newQuery().results(), entitiesSearchTextProperty, entitiesSearchRegexProperty),

                buildPseudoRootTreeItem(Inventory.class.getSimpleName(), Inventory::getItems, entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(Equipment.class.getSimpleName(), Equipment::getItems, entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem("Equipment Slots", () -> Arrays.asList(Equipment.Slot.values()), entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(Bank.class.getSimpleName(), Bank::getItems, entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(Shop.class.getSimpleName(), Shop::getItems, entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem("Incoming Trade", Trade.Incoming::getItems, entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem("Outgoing Trade", Trade.Outgoing::getItems, entitiesSearchTextProperty, entitiesSearchRegexProperty),

                buildPseudoRootTreeItem(Varps.class.getSimpleName(), Varps::getLoaded, entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(Quests.class.getSimpleName(), Quests::getAll, entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(Skills.class.getSimpleName(), () -> Arrays.asList(Skill.values()), entitiesSearchTextProperty, entitiesSearchRegexProperty),
                buildPseudoRootTreeItem(Worlds.class.getSimpleName(), Worlds::getLoaded, entitiesSearchTextProperty, entitiesSearchRegexProperty)
        );
        botInterfaceProperty().get().getEventsTreeTableView().getRoot().getChildren().setAll(
                grandexchangeTreeItem = new TreeItem<>(new Pair<>(null, GrandExchangeListener.class.getSimpleName())),
                chatboxTreeItem = new TreeItem<>(new Pair<>(null, ChatboxListener.class.getSimpleName())),
                inventoryTreeItem = new TreeItem<>(new Pair<>(null, InventoryListener.class.getSimpleName())),
                skillTreeItem = new TreeItem<>(new Pair<>(null, SkillListener.class.getSimpleName())),
                varpTreeItem = new TreeItem<>(new Pair<>(null, VarpListener.class.getSimpleName())),
                varbitTreeItem = new TreeItem<>(new Pair<>(null, VarbitListener.class.getSimpleName()))
        );
        botInterfaceProperty().get().getMiscTreeTableView().getRoot().getChildren().setAll(
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Inventory.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Equipment.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Bank.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Camera.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Chatbox.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(ChatDialog.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(DepositBox.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(EnterAmountDialog.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Environment.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(GrandExchange.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Health.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(House.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(InterfaceWindows.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Keyboard.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Menu.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Mouse.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Region.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(RuneScape.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Screen.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Trade.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Trade.Outgoing.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Trade.Incoming.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Traversal.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(Wilderness.class),
                new ReflectiveTreeItem.StaticReflectiveTreeItem(WorldHop.class)
        );
        if (Environment.isOSRS()) {
            botInterfaceProperty().get().getMiscTreeTableView().getRoot().getChildren().addAll(
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(Prayer.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(Magic.class) {
                        @Override
                        public List<TreeItem<Pair<Method, Object>>> query() {
                            final List<TreeItem<Pair<Method, Object>>> results = new ArrayList<>(Arrays.asList(
                                    new StaticReflectiveTreeItem(Magic.Ancient.class),
                                    new StaticReflectiveTreeItem(Magic.Lunar.class),
                                    new StaticReflectiveTreeItem(Magic.Book.class)
                            ));
                            results.addAll(super.query());
                            return results;
                        }
                    },
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(LootingBag.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(ControlPanelTab.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(OptionsTab.class)
            );
        }
        if (Environment.isRS3()) {
            botInterfaceProperty().get().getEntitiesTreeTableView().getRoot().getChildren().add(buildPseudoRootTreeItem(ActionBar.class.getSimpleName(), ActionBar::getFilledSlots, entitiesSearchTextProperty, entitiesSearchRegexProperty));
            botInterfaceProperty().get().getEntitiesTreeTableView().getRoot().getChildren().add(buildPseudoRootTreeItem(Familiars.class.getSimpleName(), Familiars::getLoaded, entitiesSearchTextProperty, entitiesSearchRegexProperty));
            botInterfaceProperty().get().getEventsTreeTableView().getRoot().getChildren().add(moneyPouchTreeItem = new TreeItem<>(new Pair<>(null, MoneyPouchListener.class.getSimpleName())));
            botInterfaceProperty().get().getMiscTreeTableView().getRoot().getChildren().addAll(
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(LegacyTab.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(ActionBar.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(ActionWindow.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(BeastOfBurden.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(Summoning.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(Lodestone.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(MakeXInterface.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(LootInventory.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(MoneyPouch.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(GraphicsConfiguration.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(CombatMode.class),
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(Powers.class) {
                        @Override
                        public List<TreeItem<Pair<Method, Object>>> query() {
                            final List<TreeItem<Pair<Method, Object>>> results = new ArrayList<>(Arrays.asList(
                                    new StaticReflectiveTreeItem(Powers.Magic.class),
                                    new StaticReflectiveTreeItem(Powers.Magic.Lunar.class),
                                    new StaticReflectiveTreeItem(Powers.Magic.Ancient.class),
                                    new StaticReflectiveTreeItem(Powers.Magic.Dungeoneering.class),
                                    new StaticReflectiveTreeItem(Powers.Magic.Book.class),
                                    new StaticReflectiveTreeItem(Powers.Prayer.class),
                                    new StaticReflectiveTreeItem(Powers.Prayer.Curse.class)
                            ));
                            results.addAll(super.query());
                            return results;
                        }
                    },
                    new ReflectiveTreeItem.StaticReflectiveTreeItem(InterfaceMode.class));
        }
        botInterfaceProperty().get().getMiscTreeTableView().getRoot().getChildren().sort(Comparator.comparing(o -> ((o.getValue().getValue() instanceof Class) ? (Class) o.getValue().getValue() : o.getValue().getValue().getClass()).getSimpleName()));

        StringProperty databaseSearchTextProperty = botInterfaceProperty.get().getDatabaseSearchTextField().textProperty();
        BooleanProperty databaseSearchRegexProperty = botInterfaceProperty.get().getDatabaseSearchRegexCheckBox().selectedProperty();
        botInterfaceProperty().get().getDatabaseTreeTableView().getRoot().getChildren().setAll(Arrays.asList(
                buildPseudoRootTreeItem(GameObjectDefinition.class.getSimpleName() + 's', GameObjectDefinition::loadAll, databaseSearchTextProperty, databaseSearchRegexProperty),
                buildPseudoRootTreeItem(ItemDefinition.class.getSimpleName() + 's', () -> ItemDefinition.get(0, 50000), databaseSearchTextProperty, databaseSearchRegexProperty),
                buildPseudoRootTreeItem(NpcDefinition.class.getSimpleName() + 's', () -> NpcDefinition.get(0, 50000), databaseSearchTextProperty, databaseSearchRegexProperty),
                buildPseudoRootTreeItem(SpotAnimationDefinitions.class.getSimpleName(), SpotAnimationDefinitions::loadAll, databaseSearchTextProperty, databaseSearchRegexProperty),
                buildPseudoRootTreeItem(EnumDefinitions.class.getSimpleName(), EnumDefinitions::loadAll, databaseSearchTextProperty, databaseSearchRegexProperty),
                buildPseudoRootTreeItem(OverlayDefinitions.class.getSimpleName(), OverlayDefinitions::loadAll, databaseSearchTextProperty, databaseSearchRegexProperty),
                buildPseudoRootTreeItem(UnderlayDefinitions.class.getSimpleName(), UnderlayDefinitions::loadAll, databaseSearchTextProperty, databaseSearchRegexProperty),
                buildPseudoRootTreeItem(Materials.class.getSimpleName(), Materials::loadAll, databaseSearchTextProperty, databaseSearchRegexProperty),
                buildPseudoRootTreeItem(Varbits.class.getSimpleName(), Varbits::loadAll, databaseSearchTextProperty, databaseSearchRegexProperty)
        ));
        if (!Mouse.isInputAllowed()) {
            Mouse.toggleInput();
        }
        if (!Keyboard.isInputAllowed()) {
            Keyboard.toggleInput();
        }
        setLoopDelay(2000);
        getEventDispatcher().addListener(this);
    }

    @Override
    public ObjectProperty<DevelopmentToolkitPage> botInterfaceProperty() {
        if (botInterfaceProperty == null) {
            try {
                developmentToolkitPage = new DevelopmentToolkitPage();
            } catch (IOException ioe) {
                System.err.println("Failed to load Development Toolkit UI.");
                ioe.printStackTrace();
            }
            botInterfaceProperty = new SimpleObjectProperty<>(developmentToolkitPage);
        }
        return botInterfaceProperty;
    }

    private TreeItem<Pair<Method, Object>> buildPseudoRootTreeItem(final String name, final Callable<Collection<?>> query, final StringProperty searchTextProperty, final BooleanProperty searchRegexProperty) {
        return new QueriableTreeItem<Pair<Method, Object>>(new Pair<>(null, name)) {
            @Override
            public List<TreeItem<Pair<Method, Object>>> query() {
                String searchText = searchTextProperty != null ? searchTextProperty.get() : null;
                Predicate<Pair<Method, Object>> filter = searchText != null && !searchText.isEmpty() ? new RegexSearchPredicate(searchRegexProperty, searchText) : null;
                Predicate<ReflectiveTreeItem> treeItemPredicate = parent -> {
                    if (parent.getValue() == null) {
                        return false;
                    }
                    if (filter == null || filter.test(parent.getValue())) {
                        return true;
                    }
                    List<TreeItem<Pair<Method, Object>>> children = parent.query();
                    return children != null && children.stream().anyMatch(child -> child.getValue() != null && filter.test(child.getValue()));
                };
                try {
                    return query.call().stream().map(i -> new ReflectiveTreeItem(null, i)).filter(treeItemPredicate).collect(Collectors.toList());
                } catch (Exception ex) {
                    System.err.println("Point B Thread: " + Thread.currentThread().getName());
                    e.printStackTrace();
                }
                return Collections.emptyList();
            }

            @Override
            public boolean isLeaf() {
                return false;
            }
        };
    }

    @Override
    public void onLoop() {
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        if (!developmentToolkitPage.getEventsTitledPane().isExpanded()) {
            return;
        }
        Platform.runLater(() -> chatboxTreeItem.getChildren().add(new ReflectiveTreeItem(null, event)));
    }

    @Override
    public void onContentsChanged(MoneyPouchEvent event) {
        if (!developmentToolkitPage.getEventsTitledPane().isExpanded()) {
            return;
        }
        Platform.runLater(() -> moneyPouchTreeItem.getChildren().add(new ReflectiveTreeItem(null, event)));
    }

    @Override
    public void onItemAdded(ItemEvent event) {
        if (!developmentToolkitPage.getEventsTitledPane().isExpanded()) {
            return;
        }
        Platform.runLater(() -> inventoryTreeItem.getChildren().add(new ReflectiveTreeItem(null, event)));
    }

    @Override
    public void onItemRemoved(ItemEvent event) {
        if (!developmentToolkitPage.getEventsTitledPane().isExpanded()) {
            return;
        }
        Platform.runLater(() -> inventoryTreeItem.getChildren().add(new ReflectiveTreeItem(null, event)));
    }

    @Override
    public void onLevelUp(SkillEvent event) {
        if (!developmentToolkitPage.getEventsTitledPane().isExpanded()) {
            return;
        }
        Platform.runLater(() -> skillTreeItem.getChildren().add(new ReflectiveTreeItem(null, event)));
    }

    @Override
    public void onExperienceGained(SkillEvent event) {
        if (!developmentToolkitPage.getEventsTitledPane().isExpanded()) {
            return;
        }
        Platform.runLater(() -> skillTreeItem.getChildren().add(new ReflectiveTreeItem(null, event)));
    }

    @Override
    public void onValueChanged(VarpEvent event) {
        if (!developmentToolkitPage.getEventsTitledPane().isExpanded()) {
            return;
        }
        Platform.runLater(() -> varpTreeItem.getChildren().add(new ReflectiveTreeItem(null, event)));
    }

    @Override
    public void onValueChanged(VarbitEvent event) {
        if (!developmentToolkitPage.getEventsTitledPane().isExpanded()) {
            return;
        }
        Platform.runLater(() -> varbitTreeItem.getChildren().add(new ReflectiveTreeItem(null, event)));
    }

    @Override
    public void onSlotUpdated(GrandExchangeEvent event) {
        if (!developmentToolkitPage.getEventsTitledPane().isExpanded()) {
            return;
        }
        Platform.runLater(() -> grandexchangeTreeItem.getChildren().add(new ReflectiveTreeItem(null, event)));
    }

    private static final class RegexSearchPredicate implements Predicate<Pair<Method, Object>> {
        private final Pattern pattern;

        private RegexSearchPredicate(BooleanProperty searchRegexProperty, String searchText) {
            pattern = Pattern.compile(searchRegexProperty != null && searchRegexProperty.get() ? searchText : ".*" + searchText + ".*");
        }

        @Override
        public boolean test(Pair<Method, Object> pair) {
            return pattern.matcher((pair.getKey() != null ? pair.getKey().getName() + '=' : "") + DevelopmentToolkitPage.cleanToString(pair.getValue())).matches();
        }
    }
}

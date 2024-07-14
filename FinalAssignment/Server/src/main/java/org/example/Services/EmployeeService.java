package org.example.Services;

import org.example.Dto.EmployeeMenuDto;
import org.example.Dto.ProfileDto;
import org.example.Dto.RatingDto;
import org.example.Entity.FoodItem;
import org.example.Entity.MealPreference;
import org.example.Entity.Notification;
import org.example.Entity.Profile;
import org.example.Entity.Rating;
import org.example.Entity.Region;
import org.example.Entity.RolloutMenuItem;
import org.example.Entity.SpiceLevel;
import org.example.Entity.User;
import org.example.Entity.VotedItem;
import org.example.Exception.NotFoundException;
import org.example.Repository.FoodItemRepository;
import org.example.Repository.MealPreferenceRepository;
import org.example.Repository.ProfileRepository;
import org.example.Repository.RatingRepository;
import org.example.Repository.RegionRepository;
import org.example.Repository.RolloutMenuItemRepository;
import org.example.Repository.SpiceLevelRepository;
import org.example.Repository.VotedItemRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeService {
    private final VotedItemRepository votedItemRepository;
    private final RatingRepository ratingRepository;
    private final FoodItemRepository foodItemRepository;
    private final NotificationService notificationService;
    private final RecommendationService recommendationService;
    private final RolloutMenuItemRepository rolloutMenuRepository;
    private final ProfileRepository profileRepository;
    private final MealPreferenceRepository mealPreferenceRepository;
    private final SpiceLevelRepository spiceLevelRepository;
    private final RegionRepository regionRepository;

    public EmployeeService(){
        votedItemRepository = new VotedItemRepository();
        ratingRepository = new RatingRepository();
        foodItemRepository = new FoodItemRepository();
        notificationService = new NotificationService();
        recommendationService = new RecommendationService();
        rolloutMenuRepository = new RolloutMenuItemRepository();
        profileRepository = new ProfileRepository();
        mealPreferenceRepository = new MealPreferenceRepository();
        spiceLevelRepository = new SpiceLevelRepository();
        regionRepository = new RegionRepository();
    }

    public List<EmployeeMenuDto> getRollOutMenu(String username){
        Profile profile = profileRepository.getUserProfile(username);
        List<EmployeeMenuDto> foodItems = rolloutMenuRepository.getMenuForEmployee(username, profile);
        return foodItems;
    }

    public String chooseItems(List<Integer> chosenFoodItemIds, String username){
        for(int foodItemId : chosenFoodItemIds){
            RolloutMenuItem rolloutMenuItem = rolloutMenuRepository.getItemsToBeVotedForToday(foodItemId);
            if(rolloutMenuItem == null){
                throw new NotFoundException("Food item has not been rolled out");
            }

            if (votedItemRepository.hasUserVotedForFoodItemToday(username, foodItemId)) {
                throw new IllegalStateException("You have already voted this food item today.");
            }

            VotedItem votedItem = buildVotedItem(foodItemId, username);
            votedItemRepository.save(votedItem);
        }
        return "You have chosen the items to be prepared for tomorrow";
    }

    public String provideRating(RatingDto rating, String username){
        boolean isExists = foodItemRepository.isExist(rating.getFoodItemId());
        if(!isExists){
            throw new NotFoundException("The food Item you rated does not exist");
        }

        if (ratingRepository.hasUserRatedToday(username, rating.getFoodItemId())) {
            throw new IllegalStateException("You have already rated this food item today.");
        }

        Rating ratingTobeAdded = buildRating(rating);
        recommendationService.updateItemAudit(rating.getFoodItemId());
        ratingRepository.save(ratingTobeAdded, username);
        return "You have rated the item successfully";
    }

    public String provideDiscardItemRating(RatingDto rating, String username){
        boolean isExists = foodItemRepository.isExist(rating.getFoodItemId());
        if(!isExists){
            throw new NotFoundException("The food Item you rated does not exist");
        }

        if (ratingRepository.hasUserRatedToday(username, rating.getFoodItemId())) {
            throw new IllegalStateException("You have already rated this food item today.");
        }

        Rating ratingTobeAdded = buildRating(rating);
        recommendationService.updateItemAudit(rating.getFoodItemId());
        ratingRepository.save(ratingTobeAdded, username);
        return "You have rated the item successfully";
    }

    public List<Notification> viewNotifications(){
        return notificationService.getNotifications();
    }

    public String updateProfile(ProfileDto profile, String username){
        Profile profileToBeAdded = buildProfile(profile);
        profileRepository.save(profileToBeAdded, username);
        return "Profile has been created successfully";
    }

    private Rating buildRating(RatingDto rating) {
        Rating ratingTobeAdded = new Rating();
        ratingTobeAdded.setRating(rating.getRating());
        ratingTobeAdded.setComment(rating.getComment());
        ratingTobeAdded.setFeedback(rating.getFeedback());
        ratingTobeAdded.setTastePreference(rating.getTastePreference());
        ratingTobeAdded.setRecipe(rating.getRecipe());
        FoodItem foodItem = new FoodItem();
        foodItem.setId(rating.getFoodItemId());
        ratingTobeAdded.setFoodItem(foodItem);
        return ratingTobeAdded;
    }
    private VotedItem buildVotedItem(int foodItemId, String username) {
        VotedItem votedItem = new VotedItem();
        votedItem.setVotingDate(new Date(Calendar.getInstance().getTimeInMillis()));
        FoodItem foodItem = new FoodItem();
        foodItem.setId(foodItemId);
        User user = new User();
        user.setUserName(username);
        votedItem.setFoodItem(foodItem);
        votedItem.setUser(user);
        return votedItem;
    }

    private Profile buildProfile(ProfileDto profile) {
        Profile profileToBeAdded = new Profile();
        profileToBeAdded.setSweetTooth(profile.isSweetTooth());

        Region region = new Region();
        region.setId(regionRepository.getByName(profile.getRegion()).get());
        profileToBeAdded.setRegion(region);

        MealPreference mealPreference = new MealPreference();
        mealPreference.setId(mealPreferenceRepository.getByName(profile.getMealPreference()).get());
        profileToBeAdded.setMealPreference(mealPreference);

        SpiceLevel spiceLevel = new SpiceLevel();
        spiceLevel.setId(spiceLevelRepository.getByName(profile.getSpiceLevel()).get());
        profileToBeAdded.setSpiceLevel(spiceLevel);

        return profileToBeAdded;
    }
}

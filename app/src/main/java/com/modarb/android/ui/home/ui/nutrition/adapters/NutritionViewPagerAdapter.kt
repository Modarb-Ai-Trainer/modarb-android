package com.modarb.android.ui.home.ui.nutrition.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.DailyRoutineViewBinding
import com.modarb.android.databinding.PlansViewBinding
import com.modarb.android.ui.home.ui.nutrition.OnMealClickListener
import com.modarb.android.ui.home.ui.nutrition.activities.AboutNutritionPlanActivity
import com.modarb.android.ui.home.ui.nutrition.domain.models.TodayMealsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.AllMealsPlansResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.daily_goals.DailyGoalsResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.my_meal_plan.MyMealPlanResponse
import com.modarb.android.ui.home.ui.nutrition.domain.models.today_intake.TodayInTakeResponse
import com.modarb.android.ui.home.ui.nutrition.models.MealDayModel
import com.modarb.android.ui.home.ui.nutrition.models.NutritionDataModel

class NutritionViewPagerAdapter(
    private val context: Context,
    private val listener: OnMealClickListener,
    private val todayMealsResponse: TodayMealsResponse,
    private val todayInTakeResponse: TodayInTakeResponse,
    private val allMealsResponse: AllMealsPlansResponse,
    private val myMealsResponse: MyMealPlanResponse,
    private val dailyGoalsResponse: DailyGoalsResponse

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> DailyRoutineViewHolder(
                DailyRoutineViewBinding.inflate(inflater, parent, false)
            )

            1 -> PlansViewHolder(
                PlansViewBinding.inflate(inflater, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DailyRoutineViewHolder -> holder.bind(listener)
            is PlansViewHolder -> holder.bind(context)
        }
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class DailyRoutineViewHolder(private val binding: DailyRoutineViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var listener: OnMealClickListener? = null

        @SuppressLint("SetTextI18n")
        fun bind(listener: OnMealClickListener) {
            this.listener = listener

            //todayInTakeCal
            binding.calProgressBar.progress =
                ((todayInTakeResponse.data.caloriesGoal - todayInTakeResponse.data.caloriesLeft).toFloat())
            binding.calProgressBar.progressMax = todayInTakeResponse.data.caloriesGoal.toFloat()
            binding.calValue.text = todayInTakeResponse.data.caloriesLeft.toString()
            // carb
            binding.carbsValue.text =
                "${todayInTakeResponse.data.carbsConsumed}/${todayInTakeResponse.data.carbsGoal}g"
            binding.carbsProgressBar.max = todayInTakeResponse.data.carbsGoal
            binding.carbsProgressBar.progress = todayInTakeResponse.data.carbsConsumed

            //protein
            binding.proteinValue.text =
                "${todayInTakeResponse.data.proteinConsumed}/${todayInTakeResponse.data.proteinGoal}g"
            binding.proteinProgressBar.max = todayInTakeResponse.data.proteinGoal
            binding.proteinProgressBar.progress = todayInTakeResponse.data.proteinConsumed

            //fats
            binding.fatsValue.text =
                "${todayInTakeResponse.data.fatConsumed}/${todayInTakeResponse.data.fatGoal}g"
            binding.fatsProgressBar.max = todayInTakeResponse.data.fatGoal
            binding.fatsProgressBar.progress = todayInTakeResponse.data.fatConsumed

            //Burned progress
            binding.burnedProgressBar.progress = todayInTakeResponse.data.caloriesBurned
            binding.burnedProgressBar.max = todayInTakeResponse.data.caloriesGoal
            binding.burnedCal.text = todayInTakeResponse.data.caloriesBurned.toString() + " Kcal"

            //Intaked progress
            binding.intakedProgressBar.progress =
                todayInTakeResponse.data.caloriesGoal - todayInTakeResponse.data.caloriesLeft
            binding.intakedProgressBar.max = todayInTakeResponse.data.caloriesGoal
            binding.intakedCal.text =
                (todayInTakeResponse.data.caloriesGoal - todayInTakeResponse.data.caloriesLeft).toString() + " Kcal"

            binding.lunchView.setOnClickListener {
                listener.onMailClick("lunch")
            }
            binding.breakFastView.setOnClickListener {
                listener.onMailClick("breakfast")
            }
            binding.snackView.setOnClickListener {
                listener.onMailClick("snack")
            }
            binding.dinnerView.setOnClickListener {
                listener.onMailClick("dinner")
            }

        }
    }

    inner class PlansViewHolder(private val binding: PlansViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var nutritionMealAdapter: NutritionMealAdapter

        fun bind(context: Context) {
            binding.nutritionPlanCardView.setOnClickListener {
                val intent = Intent(context, AboutNutritionPlanActivity::class.java)
                context.startActivity(intent)
            }

            binding.nutritionMealRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            val dataList = prepareData()
            nutritionMealAdapter = NutritionMealAdapter(dataList, context)
            binding.nutritionMealRecyclerView.adapter = nutritionMealAdapter
            binding.nutritionMealRecyclerView.isNestedScrollingEnabled = false

        }
    }

    private fun prepareData(): List<NutritionDataModel> {
        val dayNames =
            listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val mealNames = listOf("Breakfast", "Lunch", "Snack", "Dinner")


        val dataList = mutableListOf<NutritionDataModel>()

        for (day in dayNames) {
            val list: MutableList<MealDayModel> = mutableListOf()
            for (meal in mealNames) {
                val mealModel = MealDayModel("test", "test", "test", "test")
                list.add(mealModel)
            }

            val dataModel = NutritionDataModel(
                day, list
            )
            dataList.add(dataModel)
        }

        return dataList
    }
}

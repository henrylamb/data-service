package com.adp.request;


import com.adp.domain.Application;
import jakarta.annotation.PostConstruct;
import org.objectGeneration.client.Client;
import org.objectGeneration.client.Response;
import org.objectGeneration.jsonSchema.DataType;
import org.objectGeneration.jsonSchema.Definition;
import org.objectGeneration.jsonSchema.JsonUtils;
import org.objectGeneration.jsonSchema.ModelType;
import org.objectGeneration.testingLite.CodeTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApplicationGenerator {
    private Client client;

    @Autowired
    private Environment environment;

    private String password;

    private String baseUrl;

    public ApplicationGenerator() {}

    @PostConstruct
    public void init() {
        // Fetch the properties from application.properties using the environment object
        password = environment.getProperty("multiple.password");
        baseUrl = environment.getProperty("base.url");

        System.out.println("Password: " + password);
        System.out.println("Base URL: " + baseUrl);

        this.client = Client.newDefaultClient(password, baseUrl);
    }


    public Application sendApplication(Application application) throws Exception {

        Definition def = this.getDefinition(application.getJob().getModelResume(), application.getJob().getModelCoverLetter(), application.getCustomResume(), application.getCoverLetter(), ModelType.GPT3);

        // Construct the prompt by combining the model and user resume and cover letter details
        String prompt = String.format(
                "Model Resume:\n%s\n\nModel Cover Letter:\n%s\n\nUser Resume:\n%s\n\nUser Cover Letter:\n%s",
                application.getJob().getModelResume(),
                application.getJob().getModelCoverLetter(),
                application.getCustomResume(),
                application.getCoverLetter()
        );

        Response response = client.sendRequest(prompt, def);

        // Deserialize into a CodeTest object using the generic method

        return JsonUtils.deserialize(response.getData(), Application.class);
    }

    public Definition getDefinition(String modelResume, String modelCoverLetter, String userResume, String userCoverLetter, String model) throws Exception {
        String systemPromptGeneral = "You are an expert HR hiring manager with a keen eye for identifying top candidates. You will be reviewing a candidate's resume and cover letter for a specific job position. Evaluate the candidate's qualifications, work experience, and how well they align with the job requirements.";

        // Create the Definition object for the application evaluation
        Definition applicationEvaluation = new Definition();
        applicationEvaluation.setType(DataType.OBJECT);
        applicationEvaluation.setInstruction("Thoroughly analyze the candidate's application materials (resume and cover letter). Assess their suitability for the job based on their qualifications, experience, and alignment with the job's requirements. Provide an in-depth evaluation.");
        applicationEvaluation.setSystemPrompt(systemPromptGeneral);
        applicationEvaluation.setModel(model);

        Map<String, Definition> properties = new HashMap<>();

        // Define property for averageScore
        Definition averageScoreDef = new Definition();
        averageScoreDef.setType(DataType.NUMBER);
        averageScoreDef.setInstruction("Evaluate the overall quality and professionalism of the candidate's resume and cover letter. Provide a score between 1 and 100, where 1 indicates poor quality and 100 indicates exceptional quality.");
        properties.put("averageScore", averageScoreDef);

        // Define property for extenuatingCircumstancesScore
        Definition extenuatingCircumstancesScoreDef = new Definition();
        extenuatingCircumstancesScoreDef.setType(DataType.NUMBER);
        extenuatingCircumstancesScoreDef.setInstruction("Assess if the candidate mentions any special circumstances or challenges that may have affected their career path. Rate their ability to present these circumstances effectively, using a score between 1 and 100.");
        properties.put("extenuatingCircumstancesScore", extenuatingCircumstancesScoreDef);

        // Define property for trajectoryScore
        Definition trajectoryScoreDef = new Definition();
        trajectoryScoreDef.setType(DataType.NUMBER);
        trajectoryScoreDef.setInstruction("Evaluate the candidate's career growth and progression over time. Consider whether their career trajectory suggests upward mobility and potential. Provide a score between 1 and 100.");
        properties.put("trajectoryScore", trajectoryScoreDef);

        // Define property for pedigreeScore
        Definition pedigreeScoreDef = new Definition();
        pedigreeScoreDef.setType(DataType.NUMBER);
        pedigreeScoreDef.setInstruction("Assess the quality of the candidate's educational background, certifications, and professional affiliations. Provide a score between 1 and 100 based on how prestigious or relevant these credentials are for the role.");
        properties.put("pedigreeScore", pedigreeScoreDef);

        // Define property for academicAchievementScore
        Definition academicAchievementScoreDef = new Definition();
        academicAchievementScoreDef.setType(DataType.NUMBER);
        academicAchievementScoreDef.setInstruction("Evaluate the candidate's academic achievements, such as degrees, honors, and other scholastic accomplishments. Score between 1 and 100 based on their relevance and excellence.");
        properties.put("academicAchievementScore", academicAchievementScoreDef);

        // Define property for matchJobDescriptionScore
        Definition matchJobDescriptionScoreDef = new Definition();
        matchJobDescriptionScoreDef.setType(DataType.NUMBER);
        matchJobDescriptionScoreDef.setInstruction("Assess how closely the candidate's skills, experience, and qualifications match the job description. Provide a score between 1 and 100, where a higher score indicates a better match.");
        properties.put("matchJobDescriptionScore", matchJobDescriptionScoreDef);

        // Define property for pastExperienceScore
        Definition pastExperienceScoreDef = new Definition();
        pastExperienceScoreDef.setType(DataType.NUMBER);
        pastExperienceScoreDef.setInstruction("Evaluate the relevance and quality of the candidate's past work experience. Focus on roles, responsibilities, and achievements that are pertinent to the job they are applying for. Provide a score between 1 and 100.");
        properties.put("pastExperienceScore", pastExperienceScoreDef);

        // Define property for yearsOfExperience
        Definition yearsOfExperienceDef = new Definition();
        yearsOfExperienceDef.setType(DataType.NUMBER);
        yearsOfExperienceDef.setInstruction("Provide the number of years of relevant work experience the candidate has. Consider experience that directly relates to the job requirements.");
        properties.put("yearsOfExperience", yearsOfExperienceDef);

        // Define property for review
        Definition reviewDef = new Definition();
        reviewDef.setType(DataType.STRING);
        reviewDef.setInstruction("Provide a detailed review of the candidate's qualifications, experience, and overall fit for the role. Highlight strengths, any areas for improvement, and a summary of how well the candidate meets the job's requirements.");
        properties.put("review", reviewDef);

        // Set properties to the applicationEvaluation Definition
        applicationEvaluation.setProperties(properties);

        return applicationEvaluation;
    }


}

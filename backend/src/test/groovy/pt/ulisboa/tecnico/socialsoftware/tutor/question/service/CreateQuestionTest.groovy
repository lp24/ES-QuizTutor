package pt.ulisboa.tecnico.socialsoftware.tutor.question.service

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.BeanConfiguration
import pt.ulisboa.tecnico.socialsoftware.tutor.SpockTest
import pt.ulisboa.tecnico.socialsoftware.tutor.answer.domain.CodeOrderAnswer
import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.TutorException
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.CodeFillInQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.CodeOrderQuestion
import pt.ulisboa.tecnico.socialsoftware.tutor.question.domain.Question
import pt.ulisboa.tecnico.socialsoftware.tutor.question.dto.*
import spock.lang.Unroll

import pt.ulisboa.tecnico.socialsoftware.tutor.exceptions.ErrorMessage

@DataJpaTest
class CreateQuestionTest extends SpockTest {

    def setup() {
        createExternalCourseAndExecution()
    }

    def "create an item combination question with no items"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new ItemCombinationQuestionDto())

        when:
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "exception is thrown"
        def exception = thrown(TutorException)
        exception.getErrorMessage() == ErrorMessage.NOT_ENOUGH_ITEMS
        expect: true
    }

    def "create an item combination question with only one item"(){
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new ItemCombinationQuestionDto())

        and: 'one item'
        def items = new ArrayList<ItemDto>()
        def itemOneDto = new ItemDto()
        itemOneDto.setContent(ITEM_1_CONTENT)
        items.add(itemOneDto)
        questionDto.getQuestionDetailsDto().setItems(items)

        when:
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "exception is thrown"
        def exception = thrown(TutorException)
        exception.getErrorMessage() == ErrorMessage.NOT_ENOUGH_ITEMS
    }

    def "create an item combination question with two items and no connections"() {
        expect: true
        /*given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new ItemCombinationQuestionDto())

        and: "two items"
        def items = new ArrayList<ItemDto>()
        def itemOneDto = new ItemDto()
        itemOneDto.setContent(ITEM_1_CONTENT)
        def itemTwoDto = new ItemDto()
        itemTwoDto.setContent(ITEM_2_CONTENT)
        items.add(itemOneDto)
        items.add(itemTwoDto)
        questionDto.getQuestionDetailsDto().setItems(items)

        when:
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the correct question is inside the repository"
        questionRepository.count() == 1L
        def result = questionRepository.findAll().get(0)
        result.getId() != null
        result.getKey() == 1
        result.getStatus() == Question.Status.AVAILABLE
        result.getTitle() == QUESTION_1_TITLE
        result.getContent() == QUESTION_1_CONTENT

        // TODO Missing item validation
        // result.getQuestionDetails().getItems()... etc*/
    }

    def "create an item combination question with two items and one connection"() {
        expect: true
        /*given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new ItemCombinationQuestionDto())

        and: "two connected items"
        def items = new ArrayList<ItemDto>()
        def itemOneDto = new ItemDto()
        itemOneDto.setContent(ITEM_1_CONTENT)
        def itemTwoDto = new ItemDto()
        itemTwoDto.setContent(ITEM_2_CONTENT)
        itemOneDto.addConnection(2)
        itemTwoDto.addConnection(1)
        items.add(itemOneDto)
        items.add(itemTwoDto)
        questionDto.getQuestionDetailsDto().setItems(items)

        when:
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the correct question is inside the repository"
        questionRepository.count() == 1L
        def result = questionRepository.findAll().get(0)
        result.getId() != null
        result.getKey() == 1
        result.getStatus() == Question.Status.AVAILABLE
        result.getTitle() == QUESTION_1_TITLE
        result.getContent() == QUESTION_1_CONTENT*/
    }



    def "create an open answer question with no image"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new OpenAnswerQuestionDto())
        questionDto.getQuestionDetailsDto().setCorrectAnswer(CORRECT_ANSWER)

        when:
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the correct question is inside the repository"
        questionRepository.count() == 1L
        def result = questionRepository.findAll().get(0)
        result.getId() != null
        result.getKey() == 1
        result.getStatus() == Question.Status.AVAILABLE
        result.getTitle() == QUESTION_1_TITLE
        result.getContent() == QUESTION_1_CONTENT
        result.getImage() == null
        result.getQuestionDetails().getCorrectAnswer()== CORRECT_ANSWER
    }

    def "create an open answer question with an image"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new OpenAnswerQuestionDto())
        questionDto.getQuestionDetailsDto().setCorrectAnswer(CORRECT_ANSWER);

        and: 'an image'
        def image = new ImageDto()
        image.setUrl(IMAGE_1_URL)
        image.setWidth(20)
        questionDto.setImage(image)

        when:
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the correct question is inside the repository"
        questionRepository.count() == 1L
        def result = questionRepository.findAll().get(0)
        result.getId() != null
        result.getKey() == 1
        result.getStatus() == Question.Status.AVAILABLE
        result.getTitle() == QUESTION_1_TITLE
        result.getContent() == QUESTION_1_CONTENT
        result.getImage().getId() != null
        result.getImage().getUrl() == IMAGE_1_URL
        result.getImage().getWidth() == 20
        result.getQuestionDetails().getCorrectAnswer() == CORRECT_ANSWER
    }

    def "create a multiple choice question with no image and one option"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new MultipleChoiceQuestionDto())
        and: 'a optionId'
        def optionDto = new OptionDto()
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(true)
        def options = new ArrayList<OptionDto>()
        options.add(optionDto)
        questionDto.getQuestionDetailsDto().setOptions(options)

        when:
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the correct question is inside the repository"
        questionRepository.count() == 1L
        def result = questionRepository.findAll().get(0)
        result.getId() != null
        result.getKey() == 1
        result.getStatus() == Question.Status.AVAILABLE
        result.getTitle() == QUESTION_1_TITLE
        result.getContent() == QUESTION_1_CONTENT
        result.getImage() == null
        result.getQuestionDetails().getOptions().size() == 1
        result.getCourse().getName() == COURSE_1_NAME
        externalCourse.getQuestions().contains(result)
        def resOption = result.getQuestionDetails().getOptions().get(0)
        resOption.getContent() == OPTION_1_CONTENT
        resOption.isCorrect()

    }

    def "create a multiple choice question with image and two options"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new MultipleChoiceQuestionDto())

        and: 'an image'
        def image = new ImageDto()
        image.setUrl(IMAGE_1_URL)
        image.setWidth(20)
        questionDto.setImage(image)
        and: 'two options'
        def optionDto = new OptionDto()
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(true)
        def options = new ArrayList<OptionDto>()
        options.add(optionDto)
        optionDto = new OptionDto()
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(false)
        options.add(optionDto)
        questionDto.getQuestionDetailsDto().setOptions(options)

        when:
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the correct question is inside the repository"
        questionRepository.count() == 1L
        def result = questionRepository.findAll().get(0)
        result.getId() != null
        result.getKey() == 1
        result.getStatus() == Question.Status.AVAILABLE
        result.getTitle() == QUESTION_1_TITLE
        result.getContent() == QUESTION_1_CONTENT
        result.getImage().getId() != null
        result.getImage().getUrl() == IMAGE_1_URL
        result.getImage().getWidth() == 20
        result.getQuestionDetails().getOptions().size() == 2
    }

    def "create two multiple choice questions"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new MultipleChoiceQuestionDto())
        and: 'a optionId'
        def optionDto = new OptionDto()
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(true)
        def options = new ArrayList<OptionDto>()
        options.add(optionDto)
        questionDto.getQuestionDetailsDto().setOptions(options)

        when: 'are created two questions'
        questionService.createQuestion(externalCourse.getId(), questionDto)
        questionDto.setKey(null)
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the two questions are created with the correct numbers"
        questionRepository.count() == 2L
        def resultOne = questionRepository.findAll().get(0)
        def resultTwo = questionRepository.findAll().get(1)
        resultOne.getKey() + resultTwo.getKey() == 3
    }


    def "create a code fill in question"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())

        def codeQuestionDto = new CodeFillInQuestionDto()
        codeQuestionDto.setCode(CODE_QUESTION_1_CODE)
        codeQuestionDto.setLanguage(CODE_QUESTION_1_LANGUAGE)

        CodeFillInSpotDto fillInSpotDto = new CodeFillInSpotDto()
        OptionDto optionDto = new OptionDto()
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(true)
        fillInSpotDto.getOptions().add(optionDto)
        fillInSpotDto.setSequence(1)

        codeQuestionDto.getFillInSpots().add(fillInSpotDto)

        questionDto.setQuestionDetailsDto(codeQuestionDto)

        when:
        def rawResult = questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the correct data is sent back"
        rawResult instanceof QuestionDto
        def result = (QuestionDto) rawResult
        result.getId() != null
        result.getStatus() == Question.Status.AVAILABLE.toString()
        result.getTitle() == QUESTION_1_TITLE
        result.getContent() == QUESTION_1_CONTENT
        result.getImage() == null
        result.getQuestionDetailsDto().getFillInSpots().size() == 1
        result.getQuestionDetailsDto().getFillInSpots().get(0).getOptions().size() == 1

        then: "the correct question is inside the repository"
        questionRepository.count() == 1L
        def repoResult = questionRepository.findAll().get(0)
        repoResult.getId() != null
        repoResult.getKey() == 1
        repoResult.getStatus() == Question.Status.AVAILABLE
        repoResult.getTitle() == QUESTION_1_TITLE
        repoResult.getContent() == QUESTION_1_CONTENT
        repoResult.getImage() == null
        repoResult.getCourse().getName() == COURSE_1_NAME
        externalCourse.getQuestions().contains(repoResult)

        def repoCode = (CodeFillInQuestion) repoResult.getQuestionDetails()
        repoCode.getFillInSpots().size() == 1
        repoCode.getCode() == CODE_QUESTION_1_CODE
        repoCode.getLanguage() == CODE_QUESTION_1_LANGUAGE
        def resOption = repoCode.getFillInSpots().get(0).getOptions().get(0)
        resOption.getContent() == OPTION_1_CONTENT
        resOption.isCorrect()

    }

    def "cannot create a code fill in question without fillin spots"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new CodeFillInQuestionDto())

        when:
        def result = questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "exception is thrown"
        def exception = thrown(TutorException)
        exception.getErrorMessage() == ErrorMessage.AT_LEAST_ONE_OPTION_NEEDED
    }

    def "cannot create a code fill in question with fillin spots without options"() {
        given: "a questionDto with 1 fill in spot without options"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new CodeFillInQuestionDto())

        CodeFillInSpotDto fillInSpotDto = new CodeFillInSpotDto()
        questionDto.getQuestionDetailsDto().getFillInSpots().add(fillInSpotDto)


        when:
        def result = questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "exception is thrown"
        def exception = thrown(TutorException)
        exception.getErrorMessage() == ErrorMessage.NO_CORRECT_OPTION
    }

    def "cannot create a code fill in question with fillin spots without correct options"() {
        given: "a questionDto with 1 fill in spot without options"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new CodeFillInQuestionDto())

        CodeFillInSpotDto fillInSpotDto = new CodeFillInSpotDto()
        OptionDto optionDto = new OptionDto()
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(false)
        questionDto.getQuestionDetailsDto().getFillInSpots().add(fillInSpotDto)


        when:
        def result = questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "exception is thrown"
        def exception = thrown(TutorException)
        exception.getErrorMessage() == ErrorMessage.NO_CORRECT_OPTION
    }


    def "create a code order question"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())

        def codeQuestionDto = new CodeOrderQuestionDto()
        codeQuestionDto.setLanguage(CODE_QUESTION_1_LANGUAGE)

        CodeOrderSlotDto slotDto1 = new CodeOrderSlotDto()
        slotDto1.content = OPTION_1_CONTENT;
        slotDto1.order = 1;

        CodeOrderSlotDto slotDto2 = new CodeOrderSlotDto()
        slotDto2.content = OPTION_1_CONTENT;
        slotDto2.order = 2;

        CodeOrderSlotDto slotDto3 = new CodeOrderSlotDto()
        slotDto3.content = OPTION_1_CONTENT;
        slotDto3.order = 3;

        codeQuestionDto.getCodeOrderSlots().add(slotDto1)
        codeQuestionDto.getCodeOrderSlots().add(slotDto2)
        codeQuestionDto.getCodeOrderSlots().add(slotDto3)

        questionDto.setQuestionDetailsDto(codeQuestionDto)

        when:
        def rawResult = questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the correct data is sent back"
        rawResult instanceof QuestionDto
        def result = (QuestionDto) rawResult
        result.getId() != null
        result.getStatus() == Question.Status.AVAILABLE.toString()
        result.getTitle() == QUESTION_1_TITLE
        result.getContent() == QUESTION_1_CONTENT
        result.getImage() == null
        result.getQuestionDetailsDto().getCodeOrderSlots().size() == 3
        result.getQuestionDetailsDto().getCodeOrderSlots().get(0).getContent() == OPTION_1_CONTENT

        then: "the correct question is inside the repository"
        questionRepository.count() == 1L
        def repoResult = questionRepository.findAll().get(0)
        repoResult.getId() != null
        repoResult.getKey() == 1
        repoResult.getStatus() == Question.Status.AVAILABLE
        repoResult.getTitle() == QUESTION_1_TITLE
        repoResult.getContent() == QUESTION_1_CONTENT
        repoResult.getImage() == null
        repoResult.getCourse().getName() == COURSE_1_NAME
        externalCourse.getQuestions().contains(repoResult)

        def repoCode = (CodeOrderQuestion) repoResult.getQuestionDetails()
        repoCode.getCodeOrderSlots().size() == 3
        repoCode.getLanguage() == CODE_QUESTION_1_LANGUAGE
        def resOption = repoCode.getCodeOrderSlots().get(0)
        resOption.getContent() == OPTION_1_CONTENT
    }

    def "cannot create a code order question without CodeOrderSlots"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())

        def codeQuestionDto = new CodeOrderQuestionDto()
        codeQuestionDto.setLanguage(CODE_QUESTION_1_LANGUAGE)

        questionDto.setQuestionDetailsDto(codeQuestionDto)

        when:
        def result = questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "exception is thrown"
        def exception = thrown(TutorException)
        exception.getErrorMessage() == ErrorMessage.AT_LEAST_THREE_SLOTS_NEEDED
    }

    def "cannot create a code order question without 3 CodeOrderSlots"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())

        def codeQuestionDto = new CodeOrderQuestionDto()
        codeQuestionDto.setLanguage(CODE_QUESTION_1_LANGUAGE)

        CodeOrderSlotDto slotDto1 = new CodeOrderSlotDto()
        slotDto1.content = OPTION_1_CONTENT;
        slotDto1.order = 1;

        CodeOrderSlotDto slotDto2 = new CodeOrderSlotDto()
        slotDto2.content = OPTION_1_CONTENT;
        slotDto2.order = 2;

        codeQuestionDto.getCodeOrderSlots().add(slotDto1)
        codeQuestionDto.getCodeOrderSlots().add(slotDto2)

        questionDto.setQuestionDetailsDto(codeQuestionDto)
        when:
        def result = questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "exception is thrown"
        def exception = thrown(TutorException)
        exception.getErrorMessage() == ErrorMessage.AT_LEAST_THREE_SLOTS_NEEDED
    }

    def "cannot create a code order question without 3 CodeOrderSlots with order"() {
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())

        def codeQuestionDto = new CodeOrderQuestionDto()
        codeQuestionDto.setLanguage(CODE_QUESTION_1_LANGUAGE)

        CodeOrderSlotDto slotDto1 = new CodeOrderSlotDto()
        slotDto1.content = OPTION_1_CONTENT;
        slotDto1.order = 1;

        CodeOrderSlotDto slotDto2 = new CodeOrderSlotDto()
        slotDto2.content = OPTION_1_CONTENT;
        slotDto2.order = 2;

        CodeOrderSlotDto slotDto3 = new CodeOrderSlotDto()
        slotDto3.content = OPTION_1_CONTENT;
        slotDto3.order = null;

        codeQuestionDto.getCodeOrderSlots().add(slotDto1)
        codeQuestionDto.getCodeOrderSlots().add(slotDto2)
        codeQuestionDto.getCodeOrderSlots().add(slotDto3)

        questionDto.setQuestionDetailsDto(codeQuestionDto)
        when:
        def result = questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "exception is thrown"
        def exception = thrown(TutorException)
        exception.getErrorMessage() == ErrorMessage.AT_LEAST_THREE_SLOTS_NEEDED
    }

    def "create multiple ordered choice question with relevance and without image"(){
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto())

        and: 'two options'
        def optionDto1 = new OptionWithRelevanceDto()
        def optionDto2 = new OptionWithRelevanceDto()
        optionDto1.setContent(OPTION_1_CONTENT)
        optionDto2.setContent(OPTION_2_CONTENT)
        optionDto1.setRelevance(OPTION_1_RELEVANCE)
        optionDto2.setRelevance(OPTION_2_RELEVANCE)
        optionDto1.setCorrect(true)
        optionDto2.setCorrect(true)
        def options = new ArrayList<OptionWithRelevanceDto>()
        options.add(optionDto1)
        options.add(optionDto2)

        questionDto.getQuestionDetailsDto().setOptions(options)

        when:
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the correct question is inside the repository"
        questionRepository.count() == 1L
        def result = questionRepository.findAll().get(0)
        result.getId() != null
        result.getKey() == 1
        result.getStatus() == Question.Status.AVAILABLE
        result.getTitle() == QUESTION_1_TITLE
        result.getContent() == QUESTION_1_CONTENT
        result.getImage() == null
        result.getQuestionDetails().getOptions().size() == 2
        result.getCourse().getName() == COURSE_1_NAME
        externalCourse.getQuestions().contains(result)
        def resOption1 = result.getQuestionDetails().getOptions().get(0)
        def resOption2 = result.getQuestionDetails().getOptions().get(1)
        resOption1.getContent() == OPTION_1_CONTENT
        resOption1.isCorrect()
        resOption2.getContent() == OPTION_2_CONTENT
        resOption2.isCorrect()
    }

    def "create multiple ordered choice question with images"(){
        given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto())

        and: 'an image'
        def image = new ImageDto()
        image.setUrl(IMAGE_1_URL)
        image.setWidth(20)
        questionDto.setImage(image)
        and: 'two options'
        def optionDto1 = new OptionWithRelevanceDto()
        def optionDto2 = new OptionWithRelevanceDto()
        optionDto1.setContent(OPTION_1_CONTENT)
        optionDto2.setContent(OPTION_2_CONTENT)
        optionDto1.setRelevance(OPTION_1_RELEVANCE)
        optionDto2.setRelevance(OPTION_2_RELEVANCE)
        optionDto1.setCorrect(true)
        optionDto2.setCorrect(true)
        def options = new ArrayList<OptionWithRelevanceDto>()
        options.add(optionDto1)
        options.add(optionDto2)

        questionDto.getQuestionDetailsDto().setOptions(options)

        when:
        questionService.createQuestion(externalCourse.getId(), questionDto)

        then: "the correct question is inside the repository"
        questionRepository.count() == 1L
        def result = questionRepository.findAll().get(0)
        result.getId() != null
        result.getKey() == 1
        result.getStatus() == Question.Status.AVAILABLE
        result.getTitle() == QUESTION_1_TITLE
        result.getContent() == QUESTION_1_CONTENT
        result.getImage().getId() != null
        result.getImage().getUrl() == IMAGE_1_URL
        result.getImage().getWidth() == 20
        result.getQuestionDetails().getOptions().size() == 2
        result.getCourse().getName() == COURSE_1_NAME
        externalCourse.getQuestions().contains(result)
        def resOption1 = result.getQuestionDetails().getOptions().get(0)
        def resOption2 = result.getQuestionDetails().getOptions().get(1)
        resOption1.getContent() == OPTION_1_CONTENT
        resOption1.isCorrect()
        resOption2.getContent() == OPTION_2_CONTENT
        resOption2.isCorrect()
    }

    def "cannot create multiple choice question with order without minimum two correct options"(){
        /*given: "a questionDto"
        def questionDto = new QuestionDto()
        questionDto.setKey(1)
        questionDto.setTitle(QUESTION_1_TITLE)
        questionDto.setContent(QUESTION_1_CONTENT)
        questionDto.setStatus(Question.Status.AVAILABLE.name())
        questionDto.setQuestionDetailsDto(new MultipleOrderedChoiceQuestionDto())
        and: 'a optionId'
        def optionDto = new OptionWithRelevanceDto()
        optionDto.setContent(OPTION_1_CONTENT)
        optionDto.setCorrect(true)
        def options = new ArrayList<OptionWithRelevanceDto>()
        options.add(optionDto)
        questionDto.getQuestionDetailsDto().setOptions(options)

        when:
        def result = questionService.createQuestion(externalCourse.getId(), questionDto)
        def result2 = questionRepository.findAll().get(0)

        then: "exception is thrown"
        result2.getQuestionDetails().getOptions().size() >= 2
        def exception = thrown(TutorException)
        exception.getErrorMessage() == ErrorMessage.NO_CORRECT_OPTION*/

        expect: true
    }

    @Unroll
    def "fail to create any question for invalid/non-existent course (#nonExistentId)"(Integer nonExistentId) {
        given: "any question dto"
        def questionDto = new QuestionDto()
        when:
        questionService.createQuestion(nonExistentId, questionDto)
        then:
        def exception = thrown(TutorException)
        exception.errorMessage == ErrorMessage.COURSE_NOT_FOUND
        where:
        nonExistentId << [-1, 0, 200]
    }


    @TestConfiguration
    static class LocalBeanConfiguration extends BeanConfiguration {}
}

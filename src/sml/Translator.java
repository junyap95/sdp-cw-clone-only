package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * This class reads the lines from a .sml file and interprets them as individual instructions,
 * and then store the information in the machine
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName) {
        this.fileName = fileName;
    }

    public void readAndTranslate(Machine machine) throws IOException {
        Labels labels = machine.getLabels();
        Map<Integer, Instruction> program = machine.getProgram();

        // translate the small program in the file into
        // the labels and the program

        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();
            int programCounter = 0;

            // each iteration processes the contents of line
            // and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label, machine);
                if (instruction != null) {
                    if (label != null) labels.addLabel(label, programCounter);
                    program.put(programCounter, instruction);
                    programCounter += instruction.getSize();
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> classSupplier (String opcode) {
        Properties properties = new Properties();
        try{
            try (var fis = Translator.class.getResourceAsStream("/beans.properties")) {
                properties.load(fis);
            }
            // Obtain fully qualified class name from properties file
            String clazz = properties.getProperty(opcode);

            return Class.forName(clazz);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
        return null;
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label, Machine machine) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (line.isEmpty()) return null;
        String opcode = scan(false);
        Class<?> instructionClass = classSupplier(opcode);
        if (instructionClass != null) {
            for (Constructor<?> constructor : instructionClass.getConstructors()) {
                List<Object> parameterObjs = new ArrayList<>();

                // add label beforehand, all instructions take label as 1st arg
                parameterObjs.add(label);

                for (int i = 1; i < constructor.getParameterTypes().length; i++) {
                    Class<?> param = constructor.getParameterTypes()[i];
                    if (param.equals(InstructionDestination.class)) {
                        parameterObjs.add(getDestination(scan(true), machine));
                    } else if (param.equals(InstructionSource.class)) {
                        parameterObjs.add(getSource(scan(false), machine));
                    } else {
                        parameterObjs.add(scan(false));
                    }
                }

                return (Instruction) constructor.newInstance(parameterObjs.toArray());
            }
        }

        return null;
    }

    private InstructionSource getSource(String s, Machine machine) {
        return Optional.<InstructionSource>empty().or(() -> OperandImmediate.parseOperandImmediate(s)).or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory())).or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters())).or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters())).orElseThrow(() -> new IllegalArgumentException("invalid instruction source: " + s));
    }

    private InstructionDestination getDestination(String s, Machine machine) {
        return Optional.<InstructionDestination>empty().or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory())).or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters())).or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters())).orElseThrow(() -> new IllegalArgumentException("invalid instruction destination: " + s));
    }

    private String getLabel() {
        String word = scan(false);
        if (word.endsWith(":")) return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /**
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     *
     * @param comma remove the trailing comma if set to true
     */
    private String scan(boolean comma) {
        line = line.trim();

        int whiteSpacePosition = 0;
        while (whiteSpacePosition < line.length()) {
            if (Character.isWhitespace(line.charAt(whiteSpacePosition))) break;
            whiteSpacePosition++;
        }

        String word = line.substring(0, whiteSpacePosition);
        line = line.substring(whiteSpacePosition);
        if (comma) {
            if (word.endsWith(",")) return word.substring(0, word.length() - 1);
            throw new IllegalArgumentException("Expected a comma after " + word);
        }
        return word;
    }
}
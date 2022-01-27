// took from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}

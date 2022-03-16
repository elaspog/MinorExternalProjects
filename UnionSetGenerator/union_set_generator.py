
# pip install auto-py-to-exe
# pyinstaller --onefile halmaz_szorzo.py


import os
import glob
from functools import reduce
import itertools
import traceback
import time


MAX_NUM_CNT = 45


def read_input_files(dir_path):

    return glob.glob(dir_path)


def read_file_content(file_path):

    f = open(file_path, "r")
    return f.read()


def write_formatted_output_into_file(filename, result_set):

    f = open(filename, "w")
    f.write(format_set_structure_to_output_string(result_set))
    f.close()


def parse_data(content):

    data, metadata = {}, {}
    group_key, group_data = "", []
    for line in content.replace("\r", "").split("\n"):
        if line:
            if line[0].isalpha():
                if group_key and group_data:
                    data[group_key] = group_data
                group_key, group_data = "", []
                header_line = line.split()
                group_key = header_line[0]
                metadata[group_key] = int(line.split()[1] if len(header_line) > 1 else 1)
            else:
                group_data.append(tuple([int(x) for x in line.split(" ") if x]))
    if group_key and group_data:
        data[group_key] = group_data
    return data, metadata


def get_sorted_index_combination_list(size, count):

    index_list = list(range(0, size))
    for index_combination in itertools.combinations(index_list, count):
        yield index_combination


def get_sorted_index_repeat_combinations(len_a, len_b):

    return ((a,b) for b in range(0, len_b) for a in range(0, len_a) )


def union_of_two_sets(set_of_list_x_tuple, set_of_list_y_tuple):

    return sorted(list(set([*set_of_list_x_tuple, *set_of_list_y_tuple])))


def union_of_sets_on_parsed_data(index_combination, data_list_of_number_lists):

    if len(index_combination) < 2:
        print("Invalid combination:", index_combination)
        exit(-1)

    t = None
    for i in range(1, len(index_combination)):
        if i <= 1 and t == None:
            t = tuple(union_of_two_sets(data_list_of_number_lists[index_combination[0]], data_list_of_number_lists[index_combination[1]]))
        else:
            t = tuple(union_of_two_sets(t, data_list_of_number_lists[index_combination[i]]))
    return sorted(list(t))


def filter_citeria(x):

    if len(x) > MAX_NUM_CNT:
        return False
    return True


def format_set_structure_to_output_string(s, line_break = "\n"):

    sorted_list = sorted(list(s))
    return [ line_break.join(line_str) for line_str in [[ " ".join(num_str) for num_str in [[str(ti) for ti in li] for li in sorted_list]]]][0] + line_break


def increase_index(index_pair):

    return (index_pair[0]+1, index_pair[1]+1)


def make_union_set_with_self_by_key_list_and_keep_other_sets(parsed_data, parsed_metadata):

    processed_group_data = {}
    for key_alphanum_letter, val_list_of_number_lists in parsed_data.items():
        if key_alphanum_letter in [x for x in list(parsed_metadata.keys()) if parsed_metadata[key_alphanum_letter] > 1]:
            sorted_index_pairs = get_sorted_index_combination_list(len(val_list_of_number_lists), parsed_metadata[key_alphanum_letter])
            possible_solutions = []
            for index_combination in sorted_index_pairs:
                union_set = union_of_sets_on_parsed_data(index_combination, val_list_of_number_lists)
                if filter_citeria(union_set):
                    possible_solutions.append(tuple(union_set))
            processed_group_data[key_alphanum_letter * parsed_metadata[key_alphanum_letter]] = possible_solutions
        else:
            processed_group_data[key_alphanum_letter] = val_list_of_number_lists
    return processed_group_data


def make_union_set_with_others(dimension_dict, processed_data, last_start_time):

    result_set_collector = set()
    dimension_keys       = list(dimension_dict)
    dimension_counter    = 1
    dim_prefix_format    = '{0: <'+str(len("".join(dimension_keys)))+'}'
    idx_pair_range_left  = range(0, len(processed_data.keys())-1)

    last_step_time = last_start_time
    for idx_left in idx_pair_range_left:
    
        dimension_counter = dimension_counter + 1
        idx_right         = idx_left + 1

        key_left          = dimension_keys[idx_left]
        key_right         = dimension_keys[idx_right]
        key_prefix        = dim_prefix_format.format("".join(dimension_keys[0:dimension_counter]))
        
        dim_left          = dimension_dict[key_left] if len(result_set_collector) == 0 else len(result_set_collector)
        dim_right         = dimension_dict[key_right]
        
        counter           = 0
        total             = dim_left * dim_right
        index_pairs       = get_sorted_index_repeat_combinations(dim_left, dim_right)

        result_set_tmp    = set()
        for index_pair in index_pairs:
            counter = counter + 1
            a = processed_data[key_left] [index_pair[0]] if len(result_set_collector) == 0 else result_set_collector[index_pair[0]]
            b = processed_data[key_right][index_pair[1]]
            union_set = union_of_two_sets(a, b)
            if filter_citeria(union_set):
                result_set_tmp.add(tuple(union_set))
            print("\t", key_prefix, ":", counter, "/", total, ":", increase_index(index_pair), "     ", end="\r")
        
        current_time = time.time()
        print("\t", key_prefix, ":", counter, "/", total, ":", increase_index(index_pair))
        print("\t", key_prefix, ": %s" % len(result_set_tmp), "-", "("+str(round(current_time - last_step_time, 2))+"s)")
        last_step_time = current_time
        
        result_set_collector = list(result_set_tmp)
    
    return result_set_collector


def measure_set_sizes(data):

    dimension_dict = {}
    for key, val in data.items():
        dimension_dict[key] = len(val)
    return dimension_dict


def process_input_file(file_path, last_start_time):

    parsed_data, parsed_metadata = parse_data(read_file_content(file_path))
    print("Processing:", os.path.basename(file_path))

    dimension_dict = measure_set_sizes(parsed_data)
    print("\t input dims       :", dimension_dict)
    print("\t metadata         :", parsed_metadata)

    processed_data = make_union_set_with_self_by_key_list_and_keep_other_sets(parsed_data, parsed_metadata)
    del parsed_data
    del parsed_metadata

    dimension_dict = measure_set_sizes(processed_data)
    print("\t model dims       :", dimension_dict)

    result = []
    if len(processed_data.keys()) == 0:
        print("Error: no more keys.")
    elif len(processed_data.keys()) == 1:
        result     = processed_data[list(processed_data.keys())[0]]
    else:
        result     = make_union_set_with_others(dimension_dict, processed_data, last_start_time)
    print("\t COMPUTATION TIME :", str(round(time.time() - last_start_time, 2))+"s")
    print("\t TOTAL            :", len(result))

    return result


if __name__ == "__main__":
    try:
        files_in_input_directory = read_input_files("input/*")
        for file_path in files_in_input_directory:
            print(" ")
            last_start_time    = time.time()
            calculated_content = process_input_file(file_path, last_start_time)
            if len(calculated_content) > 0:
                write_formatted_output_into_file("output_" + os.path.basename(file_path), calculated_content)            
    except Exception as e:
        print(e)
        traceback.print_exc()

    print(" ")
    input("Press ENTER to continue...")
